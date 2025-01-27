import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cityguide.feature.auth.presentation.component.TopBar
import com.example.cityguide.feature.home.presentation.component.TopBarHome
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator


@Composable
fun DynamicTopBar(navController: NavHostController, appNavigator: AppNavigator, uiState: ProfilePageContract.UiState, onAction: (ProfilePageContract.UiAction) -> Unit) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    when (currentDestination) {
        Destination.Home() -> {
            TopBarHome(appNavigator)
        }
        Destination.Profile.fullRoute -> {
            TopBar(appNavigator,{onAction(ProfilePageContract.UiAction.EditProfileState(!uiState.editState))},{onAction(
                ProfilePageContract.UiAction.DoneEditClick)},uiState)
        }
        Destination.SignIn() -> {

        }
        else -> {
            TopBar(appNavigator)
        }
    }
}