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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.maikotrindade.nomadrewards.R
import io.github.maikotrindade.nomadrewards.model.Flight
import java.text.SimpleDateFormat

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
            } ?: run {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        modifier = Modifier.padding(vertical = 20.dp),
                        onClick = { viewModel.fetchFlights() }
                    ) {
                        Text("Reload")
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
            Spacer(modifier = Modifier.height(20.dp))
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
            Spacer(modifier = Modifier.height(20.dp))
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
                        color = colorScheme.onPrimary
                    )
                    Text(
                        text = flight.info.number,
                        color = colorScheme.onPrimary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Airline",
                        color = colorScheme.onPrimary
                    )
                    Text(
                        text = flight.airline.name,
                        color = colorScheme.onPrimary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Schedule Departure",
                        color = colorScheme.onPrimary
                    )
                    Text(
                        text = flight.departure.scheduled.formatFlightDate(),
                        color = colorScheme.onPrimary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Schedule Arrival",
                        color = colorScheme.onPrimary
                    )
                    Text(
                        text = flight.arrival.scheduled.formatFlightDate(),
                        color = colorScheme.onPrimary
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
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

@Composable
private fun String.formatFlightDate(): String {
    val configuration = LocalConfiguration.current
    val locale = configuration.locales.get(0)
    val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", locale).parse(this)
    return SimpleDateFormat("dd MMM hh:mm", locale).format(parsedDate)
}
