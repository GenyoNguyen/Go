package com.example.projectse104.domain.model

import com.example.projectse104.core.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class RideOffer(
    val id: String,
    val userId: String,
    @Serializable(with = DateSerializer::class)
    val requestTime: Date,
    @Serializable(with = DateSerializer::class)
    val estimatedDepartTime: Date,
    val startLocationId: String,
    val endLocationId: String,
    val coinCost: Int,
    @Serializable(with = DateSerializer::class)
    val acceptedTime: Date? = null,
    val rideCode: String,
    val status: String
)