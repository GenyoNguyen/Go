package com.example.projectse104.domain.model

import com.example.projectse104.core.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Message(
    val id: String? = null,
    val senderId: String? = null,
    val conversationId: String? = null,
    val content: String? = null,
    @Serializable(with = DateSerializer::class)
    val timeSent: Date? = null,
    val isRead: Boolean = false,
)
