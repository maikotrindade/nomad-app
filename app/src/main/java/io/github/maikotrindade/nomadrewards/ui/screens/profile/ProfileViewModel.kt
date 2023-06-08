package io.github.maikotrindade.nomadrewards.ui.screens.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.maikotrindade.nomadrewards.model.User
import io.github.maikotrindade.nomadrewards.network.ApiService
import io.github.maikotrindade.nomadrewards.network.NetworkUtils.performRequest
import io.github.maikotrindade.nomadrewards.ui.base.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileViewModel : ViewModel(), KoinComponent {

    private val service: ApiService by inject()
    private val userManager: UserManager by inject()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _showMessage = MutableStateFlow<String?>(null)
    val showMessage = _showMessage.asStateFlow()

    fun fetchUserByEmail() {
        viewModelScope.launch {
            _isLoading.value = true
            userManager.user.value?.email?.let { email ->
                _user.value = performRequest(
                    request = { service.getUserByEmail(email) },
                    onError = {
                        _showMessage.value = it
                    }
                )
            }
            _isLoading.value = false
        }
    }

    fun copyClipboard(context: Context, content: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(
            "Clipboard",
            content
        )
        clipboardManager.setPrimaryClip(clipData)
    }

}