package io.github.maikotrindade.nomadrewards.network

import com.google.firebase.auth.FirebaseUser
import io.github.maikotrindade.nomadrewards.model.User
import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @POST("user")
    suspend fun upsertUser(@Body user: User)
}
