package com.example.projectse104.domain.use_case.conversation

import com.example.projectse104.domain.repository.ConversationRepository
import com.example.projectse104.domain.repository.ConversationResponse
import javax.inject.Inject

class StartConversationUseCase @Inject constructor(private val conversationRepository: ConversationRepository) {
    suspend operator fun invoke(firstUserId: String, secondUserId: String): ConversationResponse =
        conversationRepository.getOrCreateConversation(firstUserId, secondUserId)
}