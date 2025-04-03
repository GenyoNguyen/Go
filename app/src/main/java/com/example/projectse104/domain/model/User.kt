package com.example.projectse104.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val fullName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phoneNumber: String? = null,
    val location: String? = null,
    val profilePic: String? = null,
    val overallRating: Float? = null,
    val coins: Int? = null
)