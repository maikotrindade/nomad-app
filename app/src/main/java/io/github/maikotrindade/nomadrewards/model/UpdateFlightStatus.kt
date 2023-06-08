package io.github.maikotrindade.nomadrewards.model

data class UpdateFlightStatus(val flightId: String, val flightStatus: Int)

enum class FlightStatus(val status: Int) {
    SCHEDULE(0),
    ACTIVE(1),
    LANDED(2),
    CANCELLED(3),
    INCIDENT(4),
    DIVERTED(5),
    REWARDED(6)
}

