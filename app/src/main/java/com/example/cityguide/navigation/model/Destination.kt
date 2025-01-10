package com.example.cityguide.navigation.model


sealed class Destination(
    protected val route: String,
    vararg params: String,
) {
    val fullRoute: String =
        if (params.isEmpty()) {
            route
        } else {
            val builder = StringBuilder(route)
            params.forEach { builder.append("/{$it}") }
            builder.toString()
        }

    sealed class NoArgumentDestination(
        route: String,
    ) : Destination(route) {
        operator fun invoke(): String = route
    }

    object Home : NoArgumentDestination("home")

    object Favorites : NoArgumentDestination("favorites")

    object SignIn : NoArgumentDestination("signIn")

    object SignUp : NoArgumentDestination("signUp")

    object ForgotPassword : NoArgumentDestination("forgotPassword")

    object Map : NoArgumentDestination("map")

    object Verification : NoArgumentDestination("verification")

    object LocationDetail : Destination("locationDetail", "locationId"){
        const val LOCATION_ID_KEY = "locationId"
        operator fun invoke(locationId: Int): String {
            return route.appendParams(
                LOCATION_ID_KEY to locationId,
            )
        }
    }

     object Profile : Destination("profile","profileImageUrl") {
         const val PROFILE_IMAGE_URL = "profileImageUrl"
         operator fun invoke(profileImgUrl: String): String {
             return route.appendParams(
                 PROFILE_IMAGE_URL to profileImgUrl,
             )
         }
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}
