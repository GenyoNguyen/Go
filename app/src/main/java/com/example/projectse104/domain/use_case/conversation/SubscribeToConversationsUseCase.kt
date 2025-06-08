package com.example.projectse104.domain.use_case.conversation

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.ConversationWithLastMessage
import com.example.projectse104.domain.repository.ConversationRepository
import com.example.projectse104.domain.repository.ConversationsWithLastMessageResponse
import com.example.projectse104.domain.repository.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SubscribeToConversationsUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val userRepository: UserRepository,
    private val getConversationListWithLastMessageUseCase: GetConversationListWithLastMessageUseCase
) {
    operator fun invoke(userId: String): Flow<ConversationsWithLastMessageResponse> = callbackFlow {
        println("Im Subscribing")
        var conversationListWithLastMessage = mutableListOf<ConversationWithLastMessage>()
        trySend(Response.Loading)
        getConversationListWithLastMessageUseCase(userId).onEach { response ->
            when (response) {
                is Response.Success -> {
                    conversationListWithLastMessage = (response.data ?: emptyList()).toMutableList()
                    println("Fetched conversation list with ${conversationListWithLastMessage.size} conversations")
                    trySend(Response.Success(conversationListWithLastMessage))
                }

                is Response.Failure -> {
                    println("Error fetching conversation list: ${response.e}")
                }

                else -> {}
            }
        }.launchIn(this)
        val newConversations = conversationRepository.subscribeToConversations(userId)
        val newMessages = conversationRepository.subscribeToMessages()

        // Collect the flow of new conversations
        newConversations.onEach { conversation ->
            try {
                val otherUserId = if (conversation.firstUserId == userId) {
                    conversation.secondUserId
                } else {
                    conversation.firstUserId
                }
                when (val otherUser = userRepository.getUser(otherUserId)) {
                    is Response.Success -> {
                        conversationListWithLastMessage.add(
                            ConversationWithLastMessage(
                                conversation = conversation,
                                otherName = otherUser.data?.fullName.toString()
                            )
                        )
                        trySend(Response.Success(conversationListWithLastMessage))
                    }

                    is Response.Failure -> {
                        throw Exception("Error fetching user: $otherUserId")
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                println("Error fetching user for conversation: ${e.message}")
            }
        }.launchIn(this)

        // Collect the flow of new messages
        newMessages.onEach { message ->
            try {
                val conversation =
                    conversationListWithLastMessage.find { it.conversation?.id.toString() == message.conversationId }
                if (conversation != null) {
                    conversation.lastMessage = message
                    trySend(Response.Success(conversationListWithLastMessage))
                }
            } catch (e: Exception) {
                println("Error updating last message: ${e.message}")
            }
        }.launchIn(this)
        awaitClose {
            println("Unsubscribing from conversations and messages")
            this.close()
        }
    }
}