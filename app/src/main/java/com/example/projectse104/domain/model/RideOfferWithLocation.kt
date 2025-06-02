package com.example.projectse104.domain.model
import kotlinx.serialization.Serializable
@Serializable
class RideOfferWithLocation (
    val rideOffer: RideOffer,
    val startLocation: String,
    val endLocation: String,
)