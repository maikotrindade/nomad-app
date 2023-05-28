package io.github.maikotrindade.nomadrewards.ui.screens.flights

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FlightsScreen(viewModel: FlightsViewModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text("FlightsScreen", color = MaterialTheme.colorScheme.onPrimary)
    }
}
