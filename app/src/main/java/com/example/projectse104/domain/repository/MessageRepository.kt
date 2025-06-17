package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Message

typealias MessageListResponse = Response<List<Message>>
typealias SendMessageResponse = Response<Unit>

interface MessageRepository {
    suspend fun getMessageList(conversationId: String): MessageListResponse

    suspend fun sendMessage(message: Message): SendMessageResponse
    suspend fun getLastMessage(conversationId: String): Response<Message>
    suspend fun updateMessageReadStatus(messageId: String, isRead: Boolean): Response<Unit>
}