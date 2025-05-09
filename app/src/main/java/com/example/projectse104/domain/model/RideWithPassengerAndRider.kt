package com.example.projectse104.domain.model

data class RideWithPassengerAndRider(
    val ride: Ride,
    val rideOffer: RideOffer,
    val passenger: User,
    val rider: User,
    val conversationId: String
)
