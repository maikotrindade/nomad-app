package io.github.maikotrindade.nomadrewards.ui.screens.welcome

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.maikotrindade.nomadrewards.R
import io.github.maikotrindade.nomadrewards.model.Flight

@Composable
fun WelcomeScreen(viewModel: WelcomeViewModel, showMessage: (String) -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.fetchFlights()
    }

    val message by viewModel.showMessage.collectAsState()
    LaunchedEffect(message) {
        message?.let { showMessage(it) }
    }

    Column(Modifier.fillMaxSize()) {
        TopContent()
        val isLoading by viewModel.isLoading.collectAsState()
        if (!isLoading) {
            val flights by viewModel.flights.collectAsState()
            flights?.let {
                FlightsList(it, viewModel)
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
private fun TopContent() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_departure),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "YYC",
                color = colorScheme.onPrimary,
                fontSize = 28.sp
            )
            Text(
                text = "Calgary International Airport",
                color = colorScheme.onPrimary,
                fontSize = 12.sp
            )
        }
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrival),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "SEA",
                color = colorScheme.onPrimary,
                fontSize = 28.sp
            )
            Text(
                text = "Seattle-Tacoma International",
                color = colorScheme.onPrimary,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun FlightsList(flights: List<Flight>, viewModel: WelcomeViewModel) {
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
                    .background(colorScheme.background, shape = RoundedCornerShape(12.dp))
                    .border(2.dp, SolidColor(Color.DarkGray), shape = RoundedCornerShape(12.dp))
            ) {
                Text(
                    text = "flightNumber: " + flight.info.number,
                    color = colorScheme.onPrimary
                )
                Text(
                    text = "airline: " + flight.airline,
                    color = colorScheme.onPrimary
                )
                Text(
                    text = "departureAirport: " + flight.departure.airport,
                    color = colorScheme.onPrimary
                )
                Text(
                    text = "arrivalAirport: " + flight.arrival.airport,
                    color = colorScheme.onPrimary
                )
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    onClick = { viewModel.onBuyTicket(flight) }
                ) {
                    Text("Buy ticket")
                }
            }
        }
    }
}

@Composable
fun LoadingAnimation(
    circleColor: Color = colorScheme.onPrimary,
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
