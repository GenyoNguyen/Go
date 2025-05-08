package com.example.projectse104.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: String,
    val name: String,
    val address: String,
    val description: String? = null
)
