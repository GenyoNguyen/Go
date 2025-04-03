package com.example.projectse104.data.repository

import com.example.projectse104.core.CONTENT_FIELD
import com.example.projectse104.core.CONVERSATION_ID_FIELD
import com.example.projectse104.core.RECEIVER_ID_FIELD
import com.example.projectse104.core.Response
import com.example.projectse104.core.SENDER_ID_FIELD
import com.example.projectse104.core.TIME_SENT_FIELD
import com.example.projectse104.domain.model.Message
import com.example.projectse104.domain.repository.MessageListResponse
import com.example.projectse104.domain.repository.MessageRepository
import com.example.projectse104.domain.repository.SendMessageResponse
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query.Direction
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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

fun DocumentSnapshot.toMessage() = Message(
    id = id,
    senderId = getString(SENDER_ID_FIELD),
    receiverId = getString(RECEIVER_ID_FIELD),
    conversationId = getString(CONVERSATION_ID_FIELD),
    content = getString(CONTENT_FIELD),
    timeSent = getDate(TIME_SENT_FIELD)
)