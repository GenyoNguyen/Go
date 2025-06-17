package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Conversation
import com.example.projectse104.domain.model.ConversationWithLastMessage
import com.example.projectse104.domain.model.Message
import kotlinx.coroutines.flow.Flow

typealias ConversationsWithLastMessageResponse = Response<List<ConversationWithLastMessage>>
typealias ConversationResponse = Response<Conversation>

interface ConversationRepository {
    suspend fun getConversationList(userId: String): Response<List<Conversation>>

    suspend fun getOrCreateConversation(
        firstUserId: String,
        secondUserId: String
    ): ConversationResponse

    suspend fun subscribeToMessagesInConversation(conversationId: String): Flow<Message>
    suspend fun subscribeToConversations(userId: String): Flow<Conversation>
    suspend fun subscribeToMessages(): Flow<Message>
}