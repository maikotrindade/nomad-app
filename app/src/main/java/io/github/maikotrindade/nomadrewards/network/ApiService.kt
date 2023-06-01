package io.github.maikotrindade.nomadrewards.network

import io.github.maikotrindade.nomadrewards.model.Flight
import io.github.maikotrindade.nomadrewards.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("user")
    suspend fun getUsers(): Response<List<User>>

    @GET("user")
    suspend fun getUserByEmail(@Query("email") email: String): Response<User>

    @POST("user")
    suspend fun upsertUser(@Body user: User)

    @POST("reward/flight")
    suspend fun createFlight(@Body id: String)

    @GET("flights")
    suspend fun getFlights(): Response<List<Flight>>
}
