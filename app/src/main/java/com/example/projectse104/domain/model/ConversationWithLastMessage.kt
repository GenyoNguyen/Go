package com.example.projectse104.domain.model

data class ConversationWithLastMessage(
    val conversation: Conversation? = null,
    var lastMessage: Message? = null,
    val otherName: String
)
