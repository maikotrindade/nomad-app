package io.github.maikotrindade.nomadrewards.ui.screens.admin

import androidx.lifecycle.ViewModel
import io.github.maikotrindade.nomadrewards.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AdminViewModel : ViewModel(), KoinComponent {

    private val service: ApiService by inject()

}