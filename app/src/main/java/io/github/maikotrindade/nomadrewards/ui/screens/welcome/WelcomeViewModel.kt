package io.github.maikotrindade.nomadrewards.ui.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.maikotrindade.nomadrewards.model.Flight
import io.github.maikotrindade.nomadrewards.network.ApiService
import io.github.maikotrindade.nomadrewards.network.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WelcomeViewModel : ViewModel(), KoinComponent {

    private val service: ApiService by inject()

    private val _flights = MutableStateFlow<List<Flight>?>(mutableListOf())
    val flights = _flights.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _showMessage = MutableStateFlow<String?>(null)
    val showMessage = _showMessage.asStateFlow()

    fun fetchFlights() = viewModelScope.launch {
        _isLoading.value = true
        _flights.value = NetworkUtils.performRequest(
            request = { service.getFlights() },
            onError = {
                _showMessage.value = it
            }
        )
        _isLoading.value = false
    }

    fun onBuyTicket(flight: Flight) {
        //activityView.showSnackBar("Flight number: " + flight.info.number)
    }
}