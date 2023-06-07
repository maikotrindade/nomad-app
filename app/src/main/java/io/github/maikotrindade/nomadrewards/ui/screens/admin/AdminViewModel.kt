package io.github.maikotrindade.nomadrewards.ui.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.maikotrindade.nomadrewards.model.Flight
import io.github.maikotrindade.nomadrewards.model.FlightStatus
import io.github.maikotrindade.nomadrewards.model.UpdateFlightStatus
import io.github.maikotrindade.nomadrewards.network.ApiService
import io.github.maikotrindade.nomadrewards.network.NetworkUtils
import io.github.maikotrindade.nomadrewards.ui.base.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AdminViewModel : ViewModel(), KoinComponent {

    private val service: ApiService by inject()
    private val userManager: UserManager by inject()

    private val _flights = MutableStateFlow<List<Flight>?>(mutableListOf())
    val flights = _flights.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _showMessage = MutableStateFlow<String?>(null)

    fun fetchFlights() = viewModelScope.launch {
        _isLoading.value = true
        _flights.value = NetworkUtils.performRequest(
            request = { service.getFlights() }
        )
        _isLoading.value = false
    }

    fun onUpdateFlightStatus(flight: Flight) = viewModelScope.launch {
        _isLoading.value = true
        service.updateFlightStatus(
            UpdateFlightStatus(flight.info.number, FlightStatus.SCHEDULE.status)
        )
        _isLoading.value = false
    }
}