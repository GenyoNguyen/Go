package com.example.projectse104.domain.model

import java.util.Date

data class Message(
    val id: String,
    val senderId: String? = null,
    val receiverId: String? = null,
    val conversationId: String? = null,
    val content: String? = null,
    val timeSent: Date? = null
)
