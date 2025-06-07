package com.example.projectse104.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.core.toCustomString
import com.example.projectse104.ui.screens.chat.Component.ChatHeader
import com.example.projectse104.ui.screens.chat.Component.ChatInputField
import com.example.projectse104.ui.screens.chat.Component.MessageItem
import com.example.projectse104.ui.screens.chat.Component.ShimmerChatDetailsScreen

@Composable
fun ChatDetailsScreen(
    navController: NavController,
    userId: String,
    otherId: String,
    viewModel: ChatDetailsViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val otherUser by viewModel.otherUser.collectAsStateWithLifecycle()
    val avatarUrl = viewModel.avatarUrl.collectAsState().value
    val listState = rememberLazyListState()

    val profilePicUrl: String? = when (avatarUrl) {
        is Response.Success<String> -> avatarUrl.data
        is Response.Failure -> null
        else -> null
    }

    // Auto scroll to bottom when new message arrives
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    if (isLoading) {
        println("Loading chat details screen...")
        ShimmerChatDetailsScreen(navController)
    } else {
        println("Chat loaded successfully")
        Scaffold(
            topBar = {
                ChatHeader(
                    navController = navController,
                    friendId = otherUser?.id.toString(),
                    name = otherUser?.fullName.toString(),
                    avatarID = R.drawable.avatar_1,
                    isActive = "yes",
                    profilePicUrl = profilePicUrl
                )
            },
            bottomBar = {
                ChatInputField(sendMessage = { message ->
                    if (message.isNotBlank()) {
                        viewModel.sendMessage(message)
                    }
                })
            },
            backgroundColor = Color(0xFFFAFAFA) // Light background like Messenger
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFAFAFA))
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    reverseLayout = true // Tin nhắn mới nhất ở dưới cùng
                ) {
                    items(messages.size) { index ->
                        val message = messages[index]
                        val content = message.content.toString()
                        val time = message.timeSent.toCustomString()
                        val isSent = message.senderId == userId // Fixed logic: true if current user sent it

                        MessageItem(
                            message = content,
                            time = time,
                            isSent = isSent,
                            profilePicUrl = if (!isSent) profilePicUrl else null
                        )
                    }
                }
            }
        }
    }
}