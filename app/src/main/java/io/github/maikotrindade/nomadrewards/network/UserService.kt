package io.github.maikotrindade.nomadrewards.network

import com.google.firebase.auth.FirebaseUser
import io.github.maikotrindade.nomadrewards.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.GET

interface UserService {
    val user: StateFlow<FirebaseUser?>

    @GET("users")
    suspend fun getUsers(): List<User>
}
