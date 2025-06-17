package com.example.projectse104.domain.use_case.conversation

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.ConversationWithLastMessage
import com.example.projectse104.domain.repository.ConversationRepository
import com.example.projectse104.domain.repository.ConversationsWithLastMessageResponse
import com.example.projectse104.domain.repository.MessageRepository
import com.example.projectse104.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetConversationListWithLastMessageUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(userId: String): Flow<ConversationsWithLastMessageResponse> = flow {
        emit(Response.Loading)
        when (val conversationListResponse = conversationRepository.getConversationList(userId)) {
            is Response.Success -> {
                val conversationList = conversationListResponse.data!!

                val conversationListWithLastMessage = mutableListOf<ConversationWithLastMessage>()
                for (conversation in conversationList) {
                    when (val lastMessage = messageRepository.getLastMessage(conversation.id)) {
                        is Response.Success -> {

                            val otherId = conversation.firstUserId
                                .takeIf { it != userId }
                                ?: conversation.secondUserId

                            val otherName =
                                when (val senderResponse = userRepository.getUser(otherId)) {
                                    is Response.Success -> {
                                        senderResponse.data!!.fullName
                                    }

                                    else -> continue
                                }

                            conversationListWithLastMessage.add(
                                ConversationWithLastMessage(
                                    conversation = conversation,
                                    lastMessage = lastMessage.data,
                                    otherName = otherName
                                )
                            )
                        }

                        is Response.Failure -> {
                            println("Failed to get last message for conversation ${conversation.id}: ${lastMessage.e}")
                        }

                        else -> continue
                    }
                }
                emit(Response.Success(conversationListWithLastMessage))
            }

            else -> {
                emit(Response.Failure(Exception("Failed to get conversation list")))
            }
        }
    }
}