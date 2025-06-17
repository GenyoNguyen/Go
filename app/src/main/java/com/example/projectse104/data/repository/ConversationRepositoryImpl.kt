package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Conversation
import com.example.projectse104.domain.model.Message
import com.example.projectse104.domain.repository.ConversationRepository
import com.example.projectse104.domain.repository.ConversationResponse
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.decodeRecord
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class ConversationRepositoryImpl(
    private val conversationsRef: PostgrestQueryBuilder,
    private val conversationChannel: RealtimeChannel,
    private val messageChannel: RealtimeChannel
) : ConversationRepository {

    override suspend fun getConversationList(userId: String):
            Response<List<Conversation>> = try {
        println(userId)
        val conversations = conversationsRef.select {
            filter {
                like("id", "%$userId%")
            }
        }.decodeList<Conversation>()
        Response.Success(conversations)
    } catch (e: Exception) {
        println("Error getting conversation list: ${e.message}")
        Response.Failure(e)
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

    override suspend fun subscribeToConversations(userId: String): Flow<Conversation> =
        callbackFlow {
            val dataFlow =
                conversationChannel.postgresChangeFlow<PostgresAction.Insert>(schema = "public") {
                    table = "Conversation"
                }
            println("Data flow: $dataFlow")

            dataFlow.onEach {
                println("Received conversation data")
                try {
                    val conversation = it.decodeRecord<Conversation>()
                    if (conversation.firstUserId == userId || conversation.secondUserId == userId) {
                        trySend(conversation)
                    }
                } catch (e: Exception) {
                    println("Error decoding conversation: ${e.message}")
                }
            }.launchIn(this)

            println("Subscribing to conversations for user: $userId")
            conversationChannel.subscribe()
            awaitClose {
                println("Unsubscribing from conversations for user: $userId")
                runBlocking {
                    conversationChannel.unsubscribe()
                }
            }
        }

    override suspend fun subscribeToMessagesInConversation(conversationId: String): Flow<Message> =
        callbackFlow {
            val dataFlow =
                conversationChannel.postgresChangeFlow<PostgresAction.Insert>(schema = "public") {
                    table = "Message"
                }
            println("Data flow: $dataFlow")

            dataFlow.onEach {
                println("Received message data in conversation")
                try {
                    val message = it.decodeRecord<Message>()
                    if (message.conversationId == conversationId) {
                        trySend(message)
                    }
                } catch (e: Exception) {
                    println("Error decoding message: ${e.message}")
                }
            }.launchIn(this)

            println("Subscribing to messages for conversation: $conversationId")
            conversationChannel.subscribe()
            awaitClose {
                println("Unsubscribing from messages for conversation: $conversationId")
                runBlocking {
                    conversationChannel.unsubscribe()
                }
            }
        }

    override suspend fun subscribeToMessages(): Flow<Message> =
        callbackFlow {
            val dataFlow =
                messageChannel.postgresChangeFlow<PostgresAction>(schema = "public") {
                    table = "Message"
                }
            println("Data flow: $dataFlow")

            dataFlow.onEach {
                println("Received message data")
                try {
                    when (it) {
                        is PostgresAction.Insert -> {
                            val message = it.decodeRecord<Message>()
                            trySend(message)
                        }

                        is PostgresAction.Update -> {
                            val message = it.decodeRecord<Message>()
                            trySend(message)
                        }

                        else -> {}
                    }
                } catch (e: Exception) {
                    println("Error decoding message: ${e.message}")
                }
            }.launchIn(this)

            println("Subscribing to messages")
            messageChannel.subscribe()
            awaitClose {
                println("Unsubscribing from messages")
                runBlocking {
                    messageChannel.unsubscribe()
                }
            }
        }
}