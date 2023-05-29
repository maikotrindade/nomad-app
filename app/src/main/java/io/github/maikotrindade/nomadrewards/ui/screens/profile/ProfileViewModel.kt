package io.github.maikotrindade.nomadrewards.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.maikotrindade.nomadrewards.model.User
import io.github.maikotrindade.nomadrewards.network.NetworkUtils
import io.github.maikotrindade.nomadrewards.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileViewModel : ViewModel(), KoinComponent {

    private val service: ApiService by inject()

    private val _users = MutableStateFlow<List<User>?>(mutableListOf())
    val users = _users.asStateFlow()

    fun fetchUsers() = viewModelScope.launch {
        _users.value = NetworkUtils.performRequest { service.getUsers() }
    }
}