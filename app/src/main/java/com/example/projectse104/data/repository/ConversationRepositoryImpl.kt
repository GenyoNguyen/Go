package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Conversation
import com.example.projectse104.domain.repository.ConversationRepository
import com.example.projectse104.domain.repository.ConversationResponse
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

    override suspend fun getOrCreateConversation(
        firstUserId: String,
        secondUserId: String
    ): ConversationResponse = try {

        val (firstOrderedUserId, secondOrderedUserId) = if (firstUserId < secondUserId) {
            firstUserId to secondUserId
        } else {
            secondUserId to firstUserId
        }

        val conversation = Conversation(
            id = "${firstOrderedUserId}_$secondOrderedUserId",
            firstUserId = firstOrderedUserId,
            secondUserId = secondOrderedUserId
        )

        conversationsRef.upsert(conversation)

        Response.Success(conversation)
    } catch (e: Exception) {
        println("Error getting or creating conversation: ${e.message}")
        Response.Failure(e)
    }
}