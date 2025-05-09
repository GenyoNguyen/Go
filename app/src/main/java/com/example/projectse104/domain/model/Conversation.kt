package com.example.projectse104.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Conversation(
    val id: String,
    val firstUserId: String,
    val secondUserId: String
)
