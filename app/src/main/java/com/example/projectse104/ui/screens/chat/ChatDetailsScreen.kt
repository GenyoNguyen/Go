package com.example.projectse104.ui.screens.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Conversation
import com.example.projectse104.ui.screens.chat.Component.ChatHeader
import com.example.projectse104.ui.screens.chat.Component.ChatInputField
import com.example.projectse104.ui.screens.chat.Component.MessageItem
import com.example.projectse104.ui.screens.chat.Component.ShimmerChatDetailsScreen

@Composable
fun ChatDetailsScreen(
    navController: NavController,
    userId: String,
    conversationId: String,
) {
    val friendName = "Nguyễn Hữu Dũng"
    val friendId = "2222"
    val messages: List<List<String>> = listOf(
        listOf("Bạn đi đến Quận 1 phải không?", "7:52", "receive"),
        listOf("Đúng vậy, tớ ở KTX khu B ấy, tòa B4?", "7:53", "send"),
        listOf(
            "Oh, tớ cũng ở gần đó, tòa B5, có gì 8h tớ khởi hành thì tớ qua rước bạn nhé. Bạn xuống trước đi tớ chuẩn bị qua ấy!",
            "7:53",
            "receive"
        ),
        listOf("Cảm ơn bạn nhiều !", "7:54", "receive"),
    )
    var isLoading: Boolean = true
    var loadingFailed: Boolean = false
    val state: Response<Conversation> = Response.Success(
        Conversation(
            id = "Lmao_Bruh",
            firstUserId = "Lmao",
            secondUserId = "Bruh"
        )
    )
    when (state) {
        is Response.Success<Conversation> -> {
            isLoading = false
            loadingFailed = false
        }

        is Response.Loading -> {
            isLoading = true
        }

        else -> {
            loadingFailed = true
        }
    }
    ToastMessage(
        message = "Không thể tải dữ liệu. Vui lòng thử lại!",
        show = loadingFailed
    )
    if (isLoading) {
        ShimmerChatDetailsScreen(navController)
    } else {
        Scaffold(
            topBar = {
                ChatHeader(
                    navController = navController,
                    friendId = friendId,
                    name = friendName,
                    avatarID = R.drawable.avatar_1,
                    isActive = "yes"
                )
            },
            bottomBar = {
                ChatInputField(sendMessage = { /* Do nothing */ })
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
                    reverseLayout = false // Đổi thành true nếu bạn muốn tin nhắn mới ở cuối màn hình (chat-style)
                ) {
                    items(messages.size) { index ->
                        val message = messages[index]
                        val content = message[0]
                        val time = message[1]
                        val type = message[2]

                        MessageItem(
                            message = content,
                            time = time,
                            type = type
                        )
                    }
                }
            }
        }
    }
}
