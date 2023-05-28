package io.github.maikotrindade.nomadrewards.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.maikotrindade.nomadrewards.ui.screens.admin.AdminScreen
import io.github.maikotrindade.nomadrewards.ui.screens.admin.AdminViewModel
import io.github.maikotrindade.nomadrewards.ui.screens.flights.FlightsScreen
import io.github.maikotrindade.nomadrewards.ui.screens.flights.FlightsViewModel
import io.github.maikotrindade.nomadrewards.ui.screens.profile.ProfileScreen
import io.github.maikotrindade.nomadrewards.ui.screens.profile.ProfileViewModel
import io.github.maikotrindade.nomadrewards.ui.screens.wallet.WalletScreen
import io.github.maikotrindade.nomadrewards.ui.screens.wallet.WalletViewModel
import io.github.maikotrindade.nomadrewards.ui.screens.welcome.WelcomeScreen
import io.github.maikotrindade.nomadrewards.ui.screens.welcome.WelcomeViewModel
import org.koin.core.component.KoinComponent

object NavigationManager : KoinComponent {

    @Composable
    fun SetupNavigation(
        header: @Composable () -> Unit,
        activity: AuthenticationUI,
        navController: NavHostController = rememberNavController()
    ) {
        ScreenContent(
            header = { header() },
            content = { NavigationBody(activity, navController) },
        )
    }

    @Composable
    private fun NavigationBody(
        activity: AuthenticationUI,
        navController: NavHostController
    ) {
        NavHost(
            navController = navController,
            startDestination = "WelcomeScreen"
        ) {
            composable("WelcomeScreen") {
                WelcomeScreen(
                    viewModel = ViewModelProvider(activity)[WelcomeViewModel::class.java],
                    navAdmin = { navController.navigate("AdminScreen") },
                    navProfile = { navController.navigate("ProfileScreen") },
                    navFlights = { navController.navigate("FlightsScreen") },
                    navWallet = { navController.navigate("WalletScreen") }
                )
            }
            composable("AdminScreen") {
                AdminScreen(ViewModelProvider(activity)[AdminViewModel::class.java])
            }
            composable("ProfileScreen") {
                ProfileScreen(ViewModelProvider(activity)[ProfileViewModel::class.java])
            }
            composable("FlightsScreen") {
                FlightsScreen(ViewModelProvider(activity)[FlightsViewModel::class.java])
            }
            composable("WalletScreen") {
                WalletScreen(ViewModelProvider(activity)[WalletViewModel::class.java])
            }
        }
    }
}