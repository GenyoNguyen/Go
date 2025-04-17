package com.example.projectse104.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserFavouriteRiderWithRider(
    val userId: String? = null,
    val riderId: String? = null,
    val rider: User,
)
