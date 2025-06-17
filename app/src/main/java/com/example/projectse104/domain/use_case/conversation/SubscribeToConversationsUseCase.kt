package com.example.projectse104.domain.use_case.conversation

import android.util.Log
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.ConversationWithLastMessage
import com.example.projectse104.domain.repository.ConversationRepository
import com.example.projectse104.domain.repository.ConversationsWithLastMessageResponse
import com.example.projectse104.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class SubscribeToConversationsUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val userRepository: UserRepository,
    private val getConversationListWithLastMessageUseCase: GetConversationListWithLastMessageUseCase
) {
    operator fun invoke(userId: String): Flow<ConversationsWithLastMessageResponse> = channelFlow {
        var conversationListWithLastMessage = mutableListOf<ConversationWithLastMessage>()
        val mutex = Mutex()

        send(Response.Success(conversationListWithLastMessage.toList()))

        supervisorScope {
            launch(Dispatchers.IO) {
                getConversationListWithLastMessageUseCase(userId).collect { response ->
                    when (response) {
                        is Response.Success -> {
                            mutex.withLock {
                                conversationListWithLastMessage =
                                    (response.data ?: emptyList()).toMutableList()
                                Log.d(
                                    "SubscribeToConversationsUseCase",
                                    "Fetched conversation list with ${conversationListWithLastMessage.size} conversations"
                                )
                                send(Response.Success(conversationListWithLastMessage.toList()))
                            }
                        }

                        is Response.Failure -> {
                            Log.d(
                                "SubscribeToConversationsUseCase",
                                "Error fetching conversation list: ${response.e}"
                            )
                        }

                        else -> {}
                    }
                }
            }

            // Collect the flow of new conversations
            launch(Dispatchers.IO) {
                Log.d("SubscribeToConversationsUseCase", "Starting to subscribe to conversations")
                conversationRepository.subscribeToConversations(userId).collect { conversation ->
                    Log.d(
                        "SubscribeToConversationsUseCase",
                        "New conversation received: $conversation"
                    )
                    try {
                        val otherUserId = if (conversation.firstUserId == userId) {
                            conversation.secondUserId
                        } else {
                            conversation.firstUserId
                        }
                        when (val otherUser = userRepository.getUser(otherUserId)) {
                            is Response.Success -> {
                                mutex.withLock {
                                    conversationListWithLastMessage.add(
                                        ConversationWithLastMessage(
                                            conversation = conversation,
                                            otherName = otherUser.data?.fullName.toString()
                                        )
                                    )
                                    Log.d(
                                        "SubscribeToConversationsUseCase",
                                        "New conversation sent: $conversation"
                                    )
                                    send(Response.Success(conversationListWithLastMessage.toList()))
                                }
                            }

                            is Response.Failure -> {
                                Log.e(
                                    "SubscribeToConversationsUseCase",
                                    "Error fetching user: $otherUserId"
                                )
                                throw Exception("Error fetching user: $otherUserId")
                            }

                            else -> {}
                        }
                    } catch (e: Exception) {
                        Log.e(
                            "SubscribeToConversationsUseCase",
                            "Error fetching user for conversation: ${e.message}"
                        )
                    }
                }
            }

            // Collect the flow of new messages
            launch(Dispatchers.IO) {
                Log.d("SubscribeToConversationsUseCase", "Starting to subscribe to messages")
                conversationRepository.subscribeToMessages().collect { message ->
                    Log.d("SubscribeToConversationsUseCase", "New message received: $message")
                    try {
                        mutex.withLock {
                            val idx = conversationListWithLastMessage.indexOfFirst { it.conversation?.id.toString() == message.conversationId }
                            if (idx != -1) {
                                val old = conversationListWithLastMessage[idx]
                                // Create a new ConversationWithLastMessage object
                                val updated = ConversationWithLastMessage(
                                    conversation = old.conversation,
                                    lastMessage = message,
                                    otherName = old.otherName
                                )
                                // Create a new list with the updated conversation
                                conversationListWithLastMessage = conversationListWithLastMessage.toMutableList().apply {
                                    set(idx, updated)
                                }
                                Log.d(
                                    "SubscribeToConversationsUseCase",
                                    "New message sent: $message"
                                )
                                send(Response.Success(conversationListWithLastMessage.toList()))
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(
                            "SubscribeToConversationsUseCase",
                            "Error updating last message: ${e.message}"
                        )
                    }
                }
            }
        }
    }.catch { e ->
        Log.e("SubscribeToConversationsUseCase", "Error in flow: ${e.message}")
        throw e
    }

//    operator fun invoke(userId: String): Flow<ConversationsWithLastMessageResponse> = flow {
//        println("Im Subscribing")
//        var conversationListWithLastMessage = mutableListOf<ConversationWithLastMessage>()
//        emit(Response.Loading)
//        getConversationListWithLastMessageUseCase(userId).collect { response ->
//            when (response) {
//                is Response.Success -> {
//                    conversationListWithLastMessage = (response.data ?: emptyList()).toMutableList()
//                    println("Fetched conversation list with ${conversationListWithLastMessage.size} conversations")
//                    emit(Response.Success(conversationListWithLastMessage))
//                }
//
//                is Response.Failure -> {
//                    println("Error fetching conversation list: ${response.e}")
//                }
//
//                else -> {}
//            }
//        }
//
//        // Collect the flow of new conversations
//        conversationRepository.subscribeToConversations(userId).collect { conversation ->
//            try {
//                val otherUserId = if (conversation.firstUserId == userId) {
//                    conversation.secondUserId
//                } else {
//                    conversation.firstUserId
//                }
//                when (val otherUser = userRepository.getUser(otherUserId)) {
//                    is Response.Success -> {
//                        conversationListWithLastMessage.add(
//                            ConversationWithLastMessage(
//                                conversation = conversation,
//                                otherName = otherUser.data?.fullName.toString()
//                            )
//                        )
//                        emit(Response.Success(conversationListWithLastMessage))
//                    }
//
//                    is Response.Failure -> {
//                        throw Exception("Error fetching user: $otherUserId")
//                    }
//
//                    else -> {}
//                }
//            } catch (e: Exception) {
//                println("Error fetching user for conversation: ${e.message}")
//            }
//        }
//
//        // Collect the flow of new messages
//        conversationRepository.subscribeToMessages().collect { message ->
//            try {
//                Log.d(this::class.toString(), "New message received: $message")
//                val conversation =
//                    conversationListWithLastMessage.find { it.conversation?.id.toString() == message.conversationId }
//                if (conversation != null) {
//                    conversation.lastMessage = message
//                    Log.d(this::class.toString(), "New message sent: $message")
//                    emit(Response.Success(conversationListWithLastMessage))
//                }
//            } catch (e: Exception) {
//                Log.e(this::class.toString(), "Error updating last message: ${e.message}")
//            }
//        }
//    }
}