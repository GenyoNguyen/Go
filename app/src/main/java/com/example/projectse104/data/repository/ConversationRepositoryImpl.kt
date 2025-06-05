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
    private val realtimeChannel: RealtimeChannel
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

    override suspend fun subscribeToMessages(conversationId: String): Flow<Message> = callbackFlow {
        val dataFlow =
            realtimeChannel.postgresChangeFlow<PostgresAction.Insert>(schema = "public") {
                table = "Message"
            }
        println("Data flow: $dataFlow")

        dataFlow.onEach {
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
        realtimeChannel.subscribe()
        awaitClose {
            println("Unsubscribing from messages for conversation: $conversationId")
            runBlocking {
                realtimeChannel.unsubscribe()
            }
        }
    }
}