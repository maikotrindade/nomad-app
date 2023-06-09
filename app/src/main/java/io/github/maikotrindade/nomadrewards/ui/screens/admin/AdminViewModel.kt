package io.github.maikotrindade.nomadrewards.ui.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.maikotrindade.nomadrewards.model.Flight
import io.github.maikotrindade.nomadrewards.model.FlightStatus
import io.github.maikotrindade.nomadrewards.model.UpdateFlightStatus
import io.github.maikotrindade.nomadrewards.network.ApiService
import io.github.maikotrindade.nomadrewards.network.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AdminViewModel : ViewModel(), KoinComponent {

    private val service: ApiService by inject()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _showMessage = MutableStateFlow<String?>(null)

    fun runForceFlightStatusActive() {
        viewModelScope.launch {
            _isLoading.value = true
            NetworkUtils.performRequestPost(
                request = { service.runForceFlightStatusActive() },
                onError = {
                    _showMessage.value = it
                }
            )
            _isLoading.value = false
        }
    }

    fun runFlightStatusViaAPI() {
        viewModelScope.launch {
            _isLoading.value = true
            NetworkUtils.performRequestPost(
                request = { service.runFlightStatusViaAPI() },
                onError = {
                    _showMessage.value = it
                }
            )
            _isLoading.value = false
        }
    }

    fun runRewardsProcess() {
        viewModelScope.launch {
            _isLoading.value = true
            NetworkUtils.performRequestPost(
                request = { service.runRewardsProcess() },
                onError = {
                    _showMessage.value = it
                }
            )
            _isLoading.value = false
        }
    }
}