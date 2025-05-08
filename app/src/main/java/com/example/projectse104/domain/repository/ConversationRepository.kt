package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Conversation
import kotlinx.coroutines.flow.Flow

typealias ConversationsWithLastMessageResponse = Response<List<Conversation>>

interface ConversationRepository {
    fun getConversationsWithLastMessage(userId: String): Flow<ConversationsWithLastMessageResponse>

}