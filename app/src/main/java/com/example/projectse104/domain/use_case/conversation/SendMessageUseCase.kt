package com.example.projectse104.domain.use_case.conversation

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Message
import com.example.projectse104.domain.repository.MessageRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    suspend operator fun invoke(
        conversationId: String,
        senderId: String,
        content: String
    ): Response<Unit> {
        return try {
            val message = Message(
                conversationId = conversationId,
                senderId = senderId,
                content = content
            )
            messageRepository.sendMessage(message)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}