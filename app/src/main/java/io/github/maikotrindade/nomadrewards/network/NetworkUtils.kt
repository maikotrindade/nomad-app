package io.github.maikotrindade.nomadrewards.network

import android.util.Log
import retrofit2.Response

object NetworkUtils {

    suspend fun <T> performRequest(
        request: suspend () -> Response<T>,
        onError: (String) -> Unit = {},
    ): T? {
        return try {
            val response = request()
            if (response.isSuccessful) {
                response.body()
            } else {
                val errorBody = response.errorBody()
                onError(errorBody.toString())
                Log.e("NetworkUtils", "response not Successful: $errorBody")
                null
            }
        } catch (exception: Exception) {
            onError(exception.message.orEmpty())
            Log.e("NetworkUtils", "exception: ${exception.message}")
            null
        }
    }
}