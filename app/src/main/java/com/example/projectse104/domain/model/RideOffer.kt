package com.example.projectse104.domain.model

import com.example.projectse104.core.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class RideOffer(
    val id: String,
    val userId: String? = null,
    @Serializable(with = DateSerializer::class)
    val requestTime: Date? = null,
    @Serializable(with = DateSerializer::class)
    val estimatedDepartTime: Date? = null,
    val startLocationId: String? = null,
    val endLocationId: String? = null,
    val coinCost: Int? = null,
    @Serializable(with = DateSerializer::class)
    val acceptedTime: Date? = null,
    val rideCode: String,
    val status: String? = null,
)