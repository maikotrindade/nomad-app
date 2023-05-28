package io.github.maikotrindade.nomadrewards.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel,
    navAdmin: () -> Unit,
    navProfile: () -> Unit,
    navFlights: () -> Unit,
    navWallet: () -> Unit,
) {
    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(colorScheme.background)
            .padding(horizontal = 20.dp),
    ) {
        NavigationButton("admin", navAdmin)
        NavigationButton("profile", navProfile)
        NavigationButton("flights", navFlights)
        NavigationButton("wallet", navWallet)
    }
}

@Composable
private fun NavigationButton(name: String, action: () -> Unit) {
    Button(
        modifier = Modifier.padding(6.dp),
        onClick = { action() }
    ) {
        Text(name, color = colorScheme.onPrimary)
    }
}

