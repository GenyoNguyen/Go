package com.example.projectse104.data.repository

import com.example.projectse104.domain.model.Message
import com.example.projectse104.domain.repository.MessageListResponse
import com.example.projectse104.domain.repository.MessageRepository
import com.example.projectse104.domain.repository.SendMessageResponse
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.flow.Flow

class MessageRepositoryImpl(
    private val messagesRef: PostgrestQueryBuilder
) : MessageRepository {
    //
//    override fun getMessageList(conversationId: String): Flow<MessageListResponse> = callbackFlow {
//        val listener = messagesRef.orderBy(TIME_SENT_FIELD, Direction.DESCENDING)
//            .addSnapshotListener { messageListSnapshot, e ->
//                val messageListResponse = if (messageListSnapshot != null) {
//                    val messageList = messageListSnapshot.map { messageSnapshot ->
//                        messageSnapshot.toMessage()
//                    }
//                    Response.Success(messageList)
//                } else {
//                    Response.Failure(e)
//                }
//                trySend(messageListResponse)
//            }
//        awaitClose {
//            listener.remove()
//        }
//    }
//
//    override suspend fun sendMessage(message: Message): SendMessageResponse = try {
//        messagesRef.add(message)
//        Response.Success(Unit)
//    } catch (e: Exception) {
//        Response.Failure(e)
//    }
    override fun getMessageList(conversationId: String): Flow<MessageListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(message: Message): SendMessageResponse {
        TODO("Not yet implemented")
    }
}