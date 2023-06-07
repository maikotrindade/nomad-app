package io.github.maikotrindade.nomadrewards.ui.screens.admin

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.maikotrindade.nomadrewards.model.Flight

@Composable
fun AdminScreen(viewModel: AdminViewModel) {
    LaunchedEffect(Unit) {
        viewModel.fetchFlights()
    }

    Column(Modifier.fillMaxSize()) {
        val isLoading by viewModel.isLoading.collectAsState()
        if (!isLoading) {
            val flights by viewModel.flights.collectAsState()
            flights?.let {
                FlightsList(it, viewModel)
            } ?: run {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        modifier = Modifier.padding(vertical = 24.dp),
                        onClick = { viewModel.fetchFlights() }
                    ) {
                        Text(
                            "Reload",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        } else {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LoadingAnimation()
            }
        }
    }
}

@Composable
private fun FlightsList(flights: List<Flight>, viewModel: AdminViewModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        flights.forEach { flight ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 12.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(2.dp, SolidColor(Color.DarkGray), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Flight number",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = flight.info.number,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
                    onClick = { viewModel.onUpdateFlightStatus(flight) }
                ) {
                    Text(
                        "Update Status to ACTIVE",
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingAnimation(
    circleColor: Color = MaterialTheme.colorScheme.onPrimary,
    animationDelay: Int = 1000
) {
    var circleScale by remember {
        mutableStateOf(0f)
    }
    val circleScaleAnimate = animateFloatAsState(
        targetValue = circleScale,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDelay
            )
        )
    )
    LaunchedEffect(Unit) {
        circleScale = 1f
    }
    Box(
        modifier = Modifier
            .size(size = 72.dp)
            .scale(scale = circleScaleAnimate.value)
            .border(
                width = 5.dp,
                color = circleColor.copy(alpha = 1 - circleScaleAnimate.value),
                shape = CircleShape
            )
    )
}
