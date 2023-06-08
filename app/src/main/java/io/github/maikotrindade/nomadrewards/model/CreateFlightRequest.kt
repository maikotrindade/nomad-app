package io.github.maikotrindade.nomadrewards.model

import com.google.gson.annotations.SerializedName

data class CreateFlightRequest(
    @SerializedName("flightId") val flightId: String,
    @SerializedName("email") val email: String
)
