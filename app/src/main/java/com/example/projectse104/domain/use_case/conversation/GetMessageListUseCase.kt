package com.example.projectse104.domain.use_case.conversation

import com.example.projectse104.domain.repository.MessageListResponse
import com.example.projectse104.domain.repository.MessageRepository
import javax.inject.Inject

class GetMessageListUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    suspend operator fun invoke(conversationId: String): MessageListResponse {
        return messageRepository.getMessageList(conversationId)
    }
}