package io.github.maikotrindade.nomadrewards.model

import com.google.gson.annotations.SerializedName

data class Flight(
    @SerializedName("flight_date") val flightDate: String,
    @SerializedName("flight_status") val flightStatus: String,
    val departure: Departure,
    val arrival: Arrival,
    val airline: Airline,
    @SerializedName("flight") val info: FlightInfo
)

data class Departure(
    val airport: String,
    val timezone: String,
    val iata: String,
    val icao: String,
    val terminal: String?,
    val gate: String?,
    val delay: Int?,
    val scheduled: String,
    val estimated: String,
    val actual: String,
    @SerializedName("estimated_runway") val estimatedRunway: String,
    @SerializedName("actual_runway") val actualRunway: String
)

data class Arrival(
    val airport: String,
    val timezone: String,
    val iata: String,
    val terminal: String?,
    val gate: String?,
    val baggage: String?,
    val delay: Int?,
    val scheduled: String,
    val estimated: String,
    val actual: String,
    @SerializedName("estimated_runway") val estimatedRunway: String,
    @SerializedName("actual_runway") val actualRunway: String
)

data class Airline(
    val name: String,
    val iata: String
)

data class FlightInfo(
    val number: String,
    val iata: String
)


