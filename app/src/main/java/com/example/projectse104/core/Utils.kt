package com.example.projectse104.core

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.projectse104.domain.repository.ConversationsWithLastMessageResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val TAG = "AppTag"

fun logErrorMessage(
    errorMessage: String
) = Log.e(TAG, errorMessage)

fun showToastMessage(
    context: Context,
    message: String
) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()

fun Date?.toCustomString(default: String = "N/A"): String {
    return if (this != null) {
        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.US)
        dateFormat.format(this)
    } else {
        default
    }
}

fun processNewChatMessages(
    conversations: ConversationsWithLastMessageResponse
): Int {
    var unreadCount = 0

    when (conversations) {
        is Response.Success -> {
            conversations.data?.forEach { conversation ->
                // Check if the last message is unread
                if (conversation.lastMessage?.isRead == false) {
                    unreadCount++
                }
            }
        }

        else -> {}
    }
    println("Total unread messages: $unreadCount")
    return unreadCount
}