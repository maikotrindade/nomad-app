package io.github.maikotrindade.nomadrewards.network

import io.github.maikotrindade.nomadrewards.model.CreateFlightRequest
import io.github.maikotrindade.nomadrewards.model.Flight
import io.github.maikotrindade.nomadrewards.model.UpdateFlightStatus
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

    @GET("flights")
    suspend fun getFlights(): Response<List<Flight>>

    @POST("reward/flight")
    suspend fun createFlight(@Body request: CreateFlightRequest)

    @POST("reward/flightstatus")
    suspend fun updateFlightStatus(@Body request: UpdateFlightStatus)


}
