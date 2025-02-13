package com.example.cityguide.feature.favorites.data.datasource

import com.example.cityguide.feature.favorites.domain.model.FavoritePlace
import com.google.firebase.firestore.FirebaseFirestore
import documentToFavoritePlace
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoritesDataSource @Inject constructor(private val firestore: FirebaseFirestore) {

    suspend fun addToFavorites(favoritePlace: FavoritePlace): Result<Unit> {
        return try {
            val favoritesRef = firestore.collection("favorites")
            favoritesRef.add(favoritePlace).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteFromFavorites(userId: String, placeId: String): Result<Unit> {
        return try {
            val favoritesRef = firestore.collection("favorites")
            val querySnapshot = favoritesRef
                .whereEqualTo("userId", userId)
                .whereEqualTo("place.placeId", placeId)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                favoritesRef.document(document.id).delete().await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFavorites(userId: String): Result<List<FavoritePlace>> {
        return try {
            val favoritesRef = firestore.collection("favorites")
            val querySnapshot = favoritesRef
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val favoritePlaces = querySnapshot.documents.mapNotNull { document ->
                documentToFavoritePlace(document)
            }
            Result.success(favoritePlaces)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}



