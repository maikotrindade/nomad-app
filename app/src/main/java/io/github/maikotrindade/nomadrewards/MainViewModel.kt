package io.github.maikotrindade.nomadrewards

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _helloWorldState = MutableStateFlow("Hello Android!")
    val helloWorldState: StateFlow<String> = _helloWorldState.asStateFlow()
}