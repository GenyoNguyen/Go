package com.example.projectse104.data.repository

import com.example.projectse104.core.RIDE_ID_FIELD
import com.example.projectse104.domain.model.Conversation
import com.example.projectse104.domain.repository.ConversaationsWithLastMessageResponse
import com.example.projectse104.domain.repository.ConversationRepository
import com.google.firebase.firestore.DocumentSnapshot
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.flow.Flow

class ConversationRepositoryImpl(
    private val conversationsRef: PostgrestQueryBuilder
) : ConversationRepository {
    override fun getConversationsWithLastMessage(userId: String): Flow<ConversaationsWithLastMessageResponse> {
        TODO("Not yet implemented")
    }

}


fun DocumentSnapshot.toConversation() = Conversation(
    id = id,
    rideId = getString(RIDE_ID_FIELD)
)