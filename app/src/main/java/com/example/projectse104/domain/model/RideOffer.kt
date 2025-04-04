package com.example.projectse104.domain.model

import java.util.Date

data class RideOffer(
    val id: String,
    val userId: String? = null,
    val requestTime: Date? = null,
    val estimatedDepartTime: Date? = null,
    val startLocation: String? = null,
    val endLocation: String? = null,
    val coinCost: Int? = null,
    val status: String? = null,
    val acceptedTime: Date? = null
)