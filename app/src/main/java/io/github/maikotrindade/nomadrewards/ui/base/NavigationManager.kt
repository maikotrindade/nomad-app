package io.github.maikotrindade.nomadrewards.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.maikotrindade.nomadrewards.MainActivity
import io.github.maikotrindade.nomadrewards.R
import io.github.maikotrindade.nomadrewards.ui.screens.admin.AdminScreen
import io.github.maikotrindade.nomadrewards.ui.screens.admin.AdminViewModel
import io.github.maikotrindade.nomadrewards.ui.screens.profile.ProfileScreen
import io.github.maikotrindade.nomadrewards.ui.screens.profile.ProfileViewModel
import io.github.maikotrindade.nomadrewards.ui.screens.wallet.WalletScreen
import io.github.maikotrindade.nomadrewards.ui.screens.wallet.WalletViewModel
import io.github.maikotrindade.nomadrewards.ui.screens.welcome.WelcomeScreen
import io.github.maikotrindade.nomadrewards.ui.screens.welcome.WelcomeViewModel
import io.github.maikotrindade.nomadrewards.ui.theme.NomadRewardsTheme
import org.koin.core.component.KoinComponent

object NavigationManager : KoinComponent {

    @Composable
    fun SetupNavigation(
        header: @Composable () -> Unit,
        activity: MainActivity,
        floating: @Composable () -> Unit,
        navController: NavHostController = rememberNavController()
    ) {
        ScreenContent(
            header = { header() },
            content = { NavigationBody(activity, navController) },
            footer = {
                Footer(
                    navAdmin = { navController.navigate("AdminScreen") },
                    navProfile = { navController.navigate("ProfileScreen") },
                    navWallet = { navController.navigate("WalletScreen") }
                )
            },
            floating = { floating() },
        )
    }

    @Composable
    fun ScreenContent(
        header: @Composable () -> Unit,
        content: @Composable () -> Unit,
        footer: @Composable () -> Unit,
        floating: @Composable () -> Unit,
    ) = NomadRewardsTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
        ) {
            Column(Modifier.fillMaxSize()) {
                header()
                Divider(color = colorScheme.onPrimary, thickness = 1.dp)
                Column(modifier = Modifier.weight(1f)) {
                    content()
                }
                footer()
            }
            floating()
        }
    }

    @Composable
    private fun NavigationBody(
        activity: MainActivity,
        navController: NavHostController
    ) {
        NavHost(
            navController = navController,
            startDestination = "WelcomeScreen"
        ) {
            composable("WelcomeScreen") {
                WelcomeScreen(
                    viewModel = ViewModelProvider(activity)[WelcomeViewModel::class.java],
                    showMessage = { message: String -> activity.showMessage(message) }
                )
            }
            composable("AdminScreen") {
                AdminScreen(ViewModelProvider(activity)[AdminViewModel::class.java])
            }
            composable("ProfileScreen") {
                ProfileScreen(
                    viewModel = ViewModelProvider(activity)[ProfileViewModel::class.java],
                    showMessage = { message: String -> activity.showMessage(message) }
                )
            }
            composable("WalletScreen") {
                WalletScreen(ViewModelProvider(activity)[WalletViewModel::class.java])
            }
        }
    }

    @Composable
    private fun Footer(
        navAdmin: () -> Unit,
        navProfile: () -> Unit,
        navWallet: () -> Unit,
    ) {
        Divider(color = colorScheme.onPrimary, thickness = 1.dp)
        Row(
            Modifier
                .background(colorScheme.background)
                .height(72.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FooterItem("profile", navProfile)
            FooterItem("wallet", navWallet)
            FooterItem("admin", navAdmin)
        }
    }

    @Composable
    private fun RowScope.FooterItem(title: String, action: () -> Unit) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = { action() }),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_profile),
                tint = colorScheme.onPrimary,
                contentDescription = null
            )
            Spacer(Modifier.height(6.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                color = colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}