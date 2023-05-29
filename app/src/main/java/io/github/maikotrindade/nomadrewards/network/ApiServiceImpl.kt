package io.github.maikotrindade.nomadrewards.network

import io.github.maikotrindade.nomadrewards.model.User
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceImpl : ApiService {
    private val client by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    override suspend fun getUsers(): Response<List<User>> {
        return client.getUsers()
    }

    override suspend fun getUserByEmail(email: String): Response<User> {
        return client.getUserByEmail(email)
    }

    override suspend fun upsertUser(user: User) {
        client.upsertUser(user)
    }

    override suspend fun createFlight(id: String) {
        client.createFlight(id)
    }

    companion object {
        const val BASE_URL = "https://nomad-core.herokuapp.com/"
    }
}