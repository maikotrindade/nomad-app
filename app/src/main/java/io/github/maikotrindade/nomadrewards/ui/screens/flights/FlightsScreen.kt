package io.github.maikotrindade.nomadrewards.ui.screens.flights

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceAround
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
fun FlightsScreen(viewModel: FlightsViewModel) {
    LaunchedEffect(Unit) {
        viewModel.fetchFlights()
    }

    val flights by viewModel.flights.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        flights.forEach { flight ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 12.dp)
                    .background(colorScheme.background, shape = RoundedCornerShape(12.dp))
                    .border(2.dp, SolidColor(Color.DarkGray), shape = RoundedCornerShape(12.dp))
            ) {
                Row(Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = SpaceAround) {
                    Text(
                        text = "flightNumber: " + flight.flightNumber,
                        color = colorScheme.onPrimary
                    )
                    Text(
                        text = "airline: " + flight.airline,
                        color = colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = SpaceAround) {
                    Text(
                        text = "departureAirport: " + flight.departureAirport,
                        color = colorScheme.onPrimary
                    )
                    Text(
                        text = "arrivalAirport: " + flight.arrivalAirport,
                        color = colorScheme.onPrimary
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    onClick = { viewModel.onBuyTicket(flight.flightNumber) }
                ) {
                    Text("Buy ticket")
                }
            }
        }
    }
}
