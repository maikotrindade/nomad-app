package io.github.maikotrindade.nomadrewards.network

import io.github.maikotrindade.nomadrewards.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("user")
    suspend fun getUsers(): Response<List<User>>

    @GET("user")
    suspend fun getUserByEmail(@Query("email") email: String): Response<User>

    @POST("user")
    suspend fun upsertUser(@Body user: User)

    @POST("reward/flight")
    suspend fun createFlight(@Body id: String)

}
