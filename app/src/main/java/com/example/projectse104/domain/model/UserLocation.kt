package com.example.projectse104.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLocation(
    val userId: String,
    val locationId: String,
    val type: String
)