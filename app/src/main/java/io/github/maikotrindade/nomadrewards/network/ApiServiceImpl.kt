package io.github.maikotrindade.nomadrewards.network

import io.github.maikotrindade.nomadrewards.model.CreateFlightRequest
import io.github.maikotrindade.nomadrewards.model.Flight
import io.github.maikotrindade.nomadrewards.model.UpdateFlightStatus
import io.github.maikotrindade.nomadrewards.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceImpl : ApiService {
    private fun getClient(url: String = BASE_URL): ApiService {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    override suspend fun getUsers(): Response<List<User>> {
        return getClient().getUsers()
    }

    override suspend fun getUserByEmail(email: String): Response<User> {
        return getClient().getUserByEmail(email)
    }

    override suspend fun upsertUser(user: User) {
        getClient().upsertUser(user)
    }

    override suspend fun createFlight(request: CreateFlightRequest) {
        getClient().createFlight(request)
    }

    override suspend fun updateFlightStatus(request: UpdateFlightStatus) {
        getClient().updateFlightStatus(request)
    }

    override suspend fun runRewardsProcess() {
        getClient().runRewardsProcess()
    }

    override suspend fun getFlights(): Response<List<Flight>> {
        return getClient().getFlights()
    }

    companion object {
        const val BASE_URL = "https://nomad-core.herokuapp.com/"
        const val TIMEOUT = 30L // in seconds
    }
}