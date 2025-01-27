import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.presentation.composable.NavigationBar

@Composable
fun DynamicBottomBar(navController: NavHostController){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    when (currentDestination) {
        Destination.Home() -> {
            NavigationBar(navController = navController)
        }
        Destination.Profile.fullRoute -> {
            NavigationBar(navController = navController)
        }
        Destination.Map.fullRoute -> {
            NavigationBar(navController = navController)
        }
        Destination.Favorites.fullRoute -> {
            NavigationBar(navController = navController)
        }
        else -> {

        }
    }
}