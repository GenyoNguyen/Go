package com.example.projectse104.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RideWithRideOffer(
    val ride: Ride,
    val rideOffer: RideOffer
)