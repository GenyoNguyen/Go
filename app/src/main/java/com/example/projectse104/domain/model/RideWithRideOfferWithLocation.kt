package com.example.projectse104.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RideWithRideOfferWithLocation(
    val ride: Ride,
    val rideOffer: RideOffer,
    val startLocation: String,
    val endLocation: String,
)
