package com.example.projectse104.domain.model

import com.example.projectse104.core.DateSerializer
import com.example.projectse104.core.RideStatus
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class RideWithRideOffer(
    val id: String,
    val rideOfferId: String? = null,
    val rideOffer: RideOffer,
    val passengerId: String? = null,
    @Serializable(with = DateSerializer::class)
    val departTime: Date? = null,
    @Serializable(with = DateSerializer::class)
    val arriveTime: Date? = null,
    val status: RideStatus = RideStatus.PENDING,
    val rating: Float? = null,
    val comment: String? = null
)