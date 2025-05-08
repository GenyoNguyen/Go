package com.example.projectse104.data.repository

import com.example.projectse104.domain.repository.ConversationRepository
import com.example.projectse104.domain.repository.ConversationsWithLastMessageResponse
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.flow.Flow

class ConversationRepositoryImpl(
    private val conversationsRef: PostgrestQueryBuilder
) : ConversationRepository {
    //    override fun getConversationsWithLastMessage(userId: String): Flow<ConversaationsWithLastMessageResponse> {
//        TODO("Not yet implemented")
//    }
    override fun getConversationsWithLastMessage(userId: String): Flow<ConversationsWithLastMessageResponse> {
        TODO("Not yet implemented")
    }

}