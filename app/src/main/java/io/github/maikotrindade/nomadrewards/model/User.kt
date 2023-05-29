package io.github.maikotrindade.nomadrewards.model

import com.google.firebase.auth.FirebaseUser

data class User(
    val name: String,
    val email: String,
    val address: String?,
    val privateKey: String?,
    val balance: Double?,
)

fun FirebaseUser.toModel() = User(
    name = this.displayName.orEmpty(),
    email = this.email.orEmpty(),
    address = null,
    privateKey = null,
    balance = null
)
