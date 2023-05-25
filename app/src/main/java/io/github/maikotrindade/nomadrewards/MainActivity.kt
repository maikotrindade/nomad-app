package io.github.maikotrindade.nomadrewards

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.maikotrindade.nomadrewards.ui.base.AuthenticationUI
import io.github.maikotrindade.nomadrewards.ui.base.ScreenContent
import io.github.maikotrindade.nomadrewards.ui.flow.flights.FlightsScreen
import io.github.maikotrindade.nomadrewards.ui.flow.profile.ProfileScreen
import io.github.maikotrindade.nomadrewards.ui.flow.welcome.WelcomeScreen

class MainActivity : AuthenticationUI() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetupNavigation()
            val viewModel: MainViewModel = viewModel()
        }
    }

    @Composable
    fun SetupNavigation(navController: NavHostController = rememberNavController()) {
        ScreenContent(
            header = { AuthHeader(userState.collectAsState().value) },
            content = {
                NavHost(
                    navController = navController,
                    startDestination = "WelcomeScreen"
                ) {
                    composable("WelcomeScreen") { WelcomeScreen() }
                    composable("ProfileScreen") { ProfileScreen() }
                    composable("FlightsScreen") { FlightsScreen() }
                }
            }
        )
    }
}
