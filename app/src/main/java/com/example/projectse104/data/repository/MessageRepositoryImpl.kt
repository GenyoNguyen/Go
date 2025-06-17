package com.example.projectse104.data.repository

import android.util.Log
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Message
import com.example.projectse104.domain.repository.MessageListResponse
import com.example.projectse104.domain.repository.MessageRepository
import com.example.projectse104.domain.repository.SendMessageResponse
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder

class MessageRepositoryImpl(
    private val messagesRef: PostgrestQueryBuilder
) : MessageRepository {

    override suspend fun getLastMessage(conversationId: String): Response<Message> = try {
        val messageResponse = messagesRef
            .select {
                filter {
                    eq("conversationId", conversationId)
                }
                order(column = "timeSent", order = Order.DESCENDING)
                limit(1)
            }.decodeSingleOrNull<Message>()

        Response.Success(messageResponse)
    } catch (e: Exception) {
        println("Error getting last message: ${e.message}")
        Response.Failure(e)
    }

    override suspend fun getMessageList(conversationId: String): MessageListResponse = try {
        val messages = messagesRef.select {
            filter {
                eq("conversationId", conversationId)
            }
            order(column = "timeSent", order = Order.DESCENDING)
        }.decodeList<Message>()
        Response.Success(messages)
    } catch (e: Exception) {
        println("Error getting message list: ${e.message}")
        Response.Failure(e)
    }

    override suspend fun sendMessage(message: Message): SendMessageResponse = try {
        println("Sending message: $message")
        messagesRef.insert(
            mapOf(
                "conversationId" to message.conversationId,
                "senderId" to message.senderId,
                "content" to message.content,
            )
        )
        Response.Success(Unit)
    } catch (e: Exception) {
        println("Error sending message: ${e.message}")
        Response.Failure(e)
    }

    override suspend fun updateMessageReadStatus(
        messageId: String,
        isRead: Boolean
    ): Response<Unit> = try {
        Log.d("MessageRepository", "Updating message read status for ID: $messageId to $isRead")
        messagesRef.update(
            mapOf("isRead" to isRead)
        ) {
            filter {
                eq("id", messageId)
            }
        }
        Response.Success(Unit)
    } catch (e: Exception) {
        Log.d("MessageRepository", "Error updating message read status: ${e.message}")
        Response.Failure(e)
    }
}