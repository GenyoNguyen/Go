package com.example.projectse104.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSavedLocation(
    val userId: String,
    val locationId: String,
    val locationName: String
)
