package io.github.maikotrindade.nomadrewards.network

import com.google.firebase.auth.FirebaseUser
import io.github.maikotrindade.nomadrewards.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserServiceImpl : UserService {
    private val client by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(UserService::class.java)
    }

    override suspend fun getUsers(): List<User> {
        return client.getUsers()
    }

    override suspend fun upsertUser(user: User) {
        client.upsertUser(user)
    }

    companion object {
        const val BASE_URL = "https://nomad-core.herokuapp.com/"
    }
}