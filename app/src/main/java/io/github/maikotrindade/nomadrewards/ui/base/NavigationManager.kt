package io.github.maikotrindade.nomadrewards.ui.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.google.firebase.auth.FirebaseUser
import io.github.maikotrindade.nomadrewards.MainActivity
import io.github.maikotrindade.nomadrewards.R
import io.github.maikotrindade.nomadrewards.ui.screens.admin.AdminScreen
import io.github.maikotrindade.nomadrewards.ui.screens.admin.AdminViewModel
import io.github.maikotrindade.nomadrewards.ui.screens.profile.ProfileScreen
import io.github.maikotrindade.nomadrewards.ui.screens.profile.ProfileViewModel
import io.github.maikotrindade.nomadrewards.ui.screens.welcome.WelcomeScreen
import io.github.maikotrindade.nomadrewards.ui.screens.welcome.WelcomeViewModel
import io.github.maikotrindade.nomadrewards.ui.theme.NomadRewardsTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object NavigationManager : KoinComponent {

    private val userManager: UserManager by inject()

    @Composable
    fun SetupNavigation(
        header: @Composable () -> Unit,
        activity: MainActivity,
        floating: @Composable () -> Unit,
        navController: NavHostController = rememberNavController(),
    ) {
        ScreenContent(
            header = { header() },
            content = { NavigationBody(activity, navController) },
            footer = {
                if (userManager.user != null) {
                    Footer(
                        navHome = { navController.navigate("WelcomeScreen") },
                        navAdmin = { navController.navigate("AdminScreen") },
                        navProfile = { navController.navigate("ProfileScreen") },
                    )
                }
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
        }
    }

    @Composable
    private fun Footer(
        navHome: () -> Unit,
        navAdmin: () -> Unit,
        navProfile: () -> Unit,
    ) {
        Divider(color = colorScheme.onPrimary, thickness = 1.dp)
        Row(
            Modifier
                .background(colorScheme.background)
                .height(72.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FooterItem("Home", R.drawable.ic_home, navHome)
            FooterItem("Admin", R.drawable.ic_admin, navAdmin)
            FooterItem("Profile", R.drawable.ic_profile, navProfile)
        }
    }

    @Composable
    private fun RowScope.FooterItem(
        title: String,
        @DrawableRes iconRes: Int,
        action: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = { action() }),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(iconRes),
                tint = colorScheme.onPrimary,
                contentDescription = null
            )
            Spacer(Modifier.height(6.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                color = colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        }
    }
}