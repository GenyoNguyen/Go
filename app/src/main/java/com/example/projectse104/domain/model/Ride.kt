package com.example.projectse104.domain.model

import com.example.projectse104.core.RideStatus
import kotlinx.serialization.Serializable

@Serializable
data class Ride(
    val id: String,
    val rideOfferId: String? = null,
    val passengerId: String? = null,
    val driverId: String? = null,
    val departTime: Date? = null,
    val arriveTime: Date? = null,
    val status: RideStatus = RideStatus.PENDING,
    val rating: Float? = null,
    val comment: String? = null
)