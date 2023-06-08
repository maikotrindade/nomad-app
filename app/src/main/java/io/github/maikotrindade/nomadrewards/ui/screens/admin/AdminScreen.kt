package io.github.maikotrindade.nomadrewards.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.maikotrindade.nomadrewards.ui.base.ComposeUtils

@Composable
fun AdminScreen(viewModel: AdminViewModel) {

    val isLoading by viewModel.isLoading.collectAsState()
    Column(Modifier.fillMaxSize()) {
        if (!isLoading) {
            TopContent(viewModel)
        } else {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ComposeUtils.LoadingAnimation()
            }
        }
    }
}

@Composable
private fun TopContent(viewModel: AdminViewModel) {
    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TopContentButton(
            title = "Trigger Backend to run a routine to force update " +
                    "all SCHEDULED flights to ACTIVE flight status",
            action = { viewModel.runForceFlightStatusActive() }
        )
        TopContentButton(
            title = "Get the latest flight statuses from the API. This will also " +
                    "update only the on-chain Scheduled flights",
            action = { viewModel.runFlightStatusViaAPI() }
        )
        TopContentButton(
            title = "Trigger Backend + Smart Contract to Run Rewards Process ⛓️ " +
                    "The smart contract will check every ACTIVE flight",
            action = { viewModel.runRewardsProcess() }
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun TopContentButton(title: String, action: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(end = 4.dp)
                .weight(1f),
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 13.sp
        )
        Button(
            onClick = action
        ) {
            Text(
                "Run",
                fontSize = 14.sp
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Divider(color = MaterialTheme.colorScheme.onPrimary, thickness = 1.dp)
    Spacer(modifier = Modifier.height(16.dp))
}