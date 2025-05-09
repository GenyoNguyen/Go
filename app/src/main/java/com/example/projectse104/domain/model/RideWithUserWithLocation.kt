package com.example.projectse104.domain.model

data class RideWithUserWithLocation(
    val ride: Ride,
    val rideOffer: RideOffer,
    val passenger: User,
    val rider: User,
    val startLocation: String,
    val endLocation: String,
)