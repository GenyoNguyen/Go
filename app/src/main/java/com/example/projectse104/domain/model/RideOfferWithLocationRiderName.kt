package com.example.projectse104.domain.model
import kotlinx.serialization.Serializable
@Serializable
class RideOfferWithLocationRider (
    val rideOffer: RideOffer,
    val startLocation: String,
    val endLocation: String,
    val rider:User
)