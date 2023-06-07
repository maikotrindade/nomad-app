package io.github.maikotrindade.nomadrewards.ui.base

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow

class UserManager {
    val user = MutableStateFlow<FirebaseUser?>(null)
}