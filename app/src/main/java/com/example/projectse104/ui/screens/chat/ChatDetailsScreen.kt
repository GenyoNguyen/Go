package com.example.projectse104.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import androidx.compose.material3.Text // For material3 Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.projectse104.*
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Conversation
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.home.ShimmerHomeScreen

@Composable
fun ChatDetailsScreen(
    navController: NavController,
    userId: String,
    conversationId: String,
) {
    val friendName = "Nguyễn Hữu Dũng"
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
    var isLoading:Boolean=true
    var loadingFailed:Boolean=false
    val state: Response<Conversation> = Response.Success(Conversation(id=conversationId, rideId = "11111111"))
    when(state){
        is Response.Success<Conversation> -> {
            isLoading=false
            loadingFailed=false
        }
        is Response.Loading -> {
            isLoading=true
        }
        else -> {
            loadingFailed=true
        }
    }
    ToastMessage(
        message = "Không thể tải dữ liệu. Vui lòng thử lại!",
        show = loadingFailed
    )
    if(isLoading){
        ShimmerChatDetailsScreen(navController)
    }
    else {
        Scaffold(
            topBar = {
                ChatHeader(
                    navController = navController,
                    name = friendName,
                    avatarID = R.drawable.avatar_1,
                    isActive = "yes"
                )
            },
            bottomBar = {
                ChatInputField()
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
