package com.example.cityguide.feature.auth.data.repository

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.cityguide.R
import com.example.cityguide.feature.auth.domain.model.SignInResult
import com.example.cityguide.feature.auth.domain.model.UserData
import com.example.cityguide.feature.auth.domain.repository.GoogleAuthRepository
import com.example.cityguide.main.util.ProfileEmailSingleton
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleAuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context:Context,
    private val oneTapClient: SignInClient,
    private val firebaseAuth:FirebaseAuth
): GoogleAuthRepository {

     override suspend fun signIn():IntentSender?{
         val result =
             try {
                 oneTapClient.beginSignIn(
                     buildSignInRequest()
                 ).await()
             }
             catch(exception:Exception) {
                 exception.printStackTrace()
                 if(exception is CancellationException) throw exception
                 null
             }
         return result?.pendingIntent?.intentSender
    }


    override suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken,null)
        return try {
            val user = firebaseAuth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                error = null
            )
        }
        catch(exception:Exception) {
            exception.printStackTrace()
            if(exception is CancellationException) throw exception
            SignInResult(
                data = null,
                error = exception.message
            )
        }
    }


    override suspend fun signOut() {
        try {
            // 1. Sign out from Firebase
            firebaseAuth.signOut()

            // 2. Clear Google One Tap session
            oneTapClient.signOut().await() // Add this line

            // 3. Clear Google Sign-In client (traditional approach)
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            googleSignInClient.signOut().await()
            googleSignInClient.revokeAccess().await()

            // 4. Clear local profile data
            ProfileImageUrlSingleton.setProfileImageUrl("")
            ProfileNameSingleton.setName("")
            ProfileEmailSingleton.setEmail("")
        } catch (exception: Exception) {
            if (exception is CancellationException) throw exception
            exception.printStackTrace()
        }
    }


    private fun buildSignInRequest():BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}