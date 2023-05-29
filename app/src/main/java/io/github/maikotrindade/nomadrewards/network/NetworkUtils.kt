package io.github.maikotrindade.nomadrewards.network

import android.util.Log
import retrofit2.Response

object NetworkUtils {

    suspend fun <T> performRequest(request: suspend () -> Response<T>): T? {
        return try {
            val response = request()
            if (response.isSuccessful) {
                response.body()
            } else {
                val errorBody = response.errorBody()
                Log.e("NetworkUtils", "response not Successful: $errorBody")
                null
            }
        } catch (exception: Exception) {
            Log.e("NetworkUtils", "exception: ${exception.message}")
            null
        }
    }
}