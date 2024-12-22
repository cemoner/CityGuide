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

    object Login : NoArgumentDestination("login")

    object Register : NoArgumentDestination("register")

    object Cart : NoArgumentDestination("cart")

    object LocationDetail : Destination("locationDetail", "locationId"){
        const val LOCATION_ID_KEY = "locationId"
        operator fun invoke(locationId: Int): String {
            return route.appendParams(
                LOCATION_ID_KEY to locationId,
            )
        }
    }

     object Profile : NoArgumentDestination("profile") {
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
