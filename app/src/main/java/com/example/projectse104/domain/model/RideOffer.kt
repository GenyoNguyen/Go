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
    val startLocation: String? = null,
    val endLocation: String? = null,
    val coinCost: Int? = null,
    val status: String? = null,
    @Serializable(with = DateSerializer::class)
    val acceptedTime: Date? = null,
    val rideCode: String
)