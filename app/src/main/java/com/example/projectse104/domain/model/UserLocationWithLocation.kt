package com.example.projectse104.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLocationWithLocation(
    val userId: String,
    val locationId: String,
    val type: LocationTypeEnum,
    val location: Location
)