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

    object Category : Destination("category","category"){

        const val CATEGORY_KEY = "category"
        operator fun invoke(category: String): String = route.appendParams(
            CATEGORY_KEY to category
        )
    }

    object PlaceDetail : Destination("placeDetail","placeId"){

        const val PLACE_ID_KEY = "place"
        operator fun invoke(placeId: String): String = route.appendParams(
            PLACE_ID_KEY to placeId
        )
    }

    object Profile : NoArgumentDestination("profile")
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
