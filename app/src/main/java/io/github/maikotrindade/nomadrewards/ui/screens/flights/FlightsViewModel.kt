package io.github.maikotrindade.nomadrewards.ui.screens.flights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.maikotrindade.nomadrewards.model.Flight
import io.github.maikotrindade.nomadrewards.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FlightsViewModel : ViewModel(), KoinComponent {

    private val service: ApiService by inject()

    private val _flights = MutableStateFlow<MutableList<Flight>>(mutableListOf())
    val flights = _flights.asStateFlow()

    fun fetchFlights() = viewModelScope.launch {
        //TODO -> _flights.value = NetworkUtils.performRequest { service.getFlights() }
        for (index in 0..10) {
            _flights.value.add(
                Flight(
                    flightNumber = "512$index",
                    departureAirport = "YYC",
                    arrivalAirport = "YVR",
                    airline = "airline$index"
                )
            )
        }
    }

    fun onBuyTicket(flightNumber: String) {
        // TODO
    }
}