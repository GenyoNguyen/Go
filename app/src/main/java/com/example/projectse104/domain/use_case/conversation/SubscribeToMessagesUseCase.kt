package com.example.projectse104.domain.use_case.conversation

import com.example.projectse104.domain.model.Message
import com.example.projectse104.domain.repository.ConversationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeToMessagesUseCase @Inject constructor(private val conversationRepository: ConversationRepository) {
    suspend operator fun invoke(conversationId: String): Flow<Message> =
        conversationRepository.subscribeToMessagesInConversation(conversationId)
}