package com.example.projectse104.ui.screens.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.R
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

    if (isLoading) {
        println("Loading chat details screen...")
        ShimmerChatDetailsScreen(navController)
    } else {
        println("Lmao")
        Scaffold(
            topBar = {
                ChatHeader(
                    navController = navController,
                    friendId = otherUser?.id.toString(),
                    name = otherUser?.fullName.toString(),
                    avatarID = R.drawable.avatar_1,
                    isActive = "yes"
                )
            },
            bottomBar = {
                ChatInputField(sendMessage = { viewModel.sendMessage(it) })
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    reverseLayout = true // Đổi thành true nếu bạn muốn tin nhắn mới ở cuối màn hình
                    // (chat-style)
                ) {
                    items(messages.size) { index ->
                        val message = messages[index]
                        val content = message.content.toString()
                        val time = message.timeSent.toCustomString()
                        val isSent = message.senderId != userId

                        MessageItem(
                            message = content,
                            time = time,
                            isSent = isSent
                        )
                    }
                }
            }
        }
    }
}
