package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Message
import kotlinx.coroutines.flow.Flow

typealias MessageListResponse = Response<List<Message>>
typealias SendMessageResponse = Response<Unit>

interface MessageRepository {
    fun getMessageList(conversationId: String): Flow<MessageListResponse>

    suspend fun sendMessage(message: Message): SendMessageResponse
}