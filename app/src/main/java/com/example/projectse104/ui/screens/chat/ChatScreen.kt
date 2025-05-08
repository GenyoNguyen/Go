package com.example.projectse104.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.Header
import com.example.projectse104.Component.ShimmerScreen
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.chat.Component.ChatItem

@Composable
fun ChatScreen(navController: NavController, userId: String) {
    val conversations: List<List<Any>> = listOf(
        listOf(
            "0001",
            "Nguyễn Hữu Dũng",
            "You: Cảm ơn bạn nhiều !",
            "4 mins",
            R.drawable.avatar_1,
            "yes",
            "yes"
        ),
        listOf("0002", "Nguyễn Minh Triết", "Thanks", "43 mins", R.drawable.avatar_2, "yes", "no"),
        listOf(
            "0003",
            "Nguyễn Phong Huy",
            "Lần sau đi nữa nhé!",
            "4 hours",
            R.drawable.avatar_1,
            "no",
            "yes"
        ),
        listOf("0004", "Xuân Phúc", "You: Oke bro", "4 days", R.drawable.avatar_2, "yes", "no"),
        listOf("0005", "Trần Văn An", "Hẹn gặp lại!", "6 days", R.drawable.avatar_1, "no", "no"),
        listOf("0006", "Lê Hoàng", "Đi vui vẻ nha!", "1 week", R.drawable.avatar_2, "yes", "yes"),
    )
    var isLoading: Boolean = true
    var loadingFailed: Boolean = false
    val state: Response<User> = Response.Success(
        User(
            id = "1111",
            fullName = "Nguyễn Xuân Phúc",
            overallRating = 5.0f,
            coins = 100,
            userCode = "kzdf2",
            vehicleId = "Lmao"
        )
    )
    when (state) {
        is Response.Success<User> -> {
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
        ShimmerScreen(navController, userId, 2)
    } else {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController, userId, 2)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
            ) {
                Header("Chat", R.drawable.chat_icon_header)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Cuộn trong phần nội dung
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    for (conversation in conversations) {
                        val conversationId = conversation[0].toString()
                        val name = conversation[1].toString()
                        val message = conversation[2].toString()
                        val time = conversation[3].toString()
                        val imageRes: Int = when (val value = conversation[4]) {
                            is Int -> value
                            is String -> value.toIntOrNull() ?: 0
                            else -> 0
                        }
                        val haveSeen = conversation[5].toString()
                        val isOnline = conversation[6].toString()

                        ChatItem(
                            navController = navController,
                            userId = userId,
                            conversationId = conversationId,
                            friendName = name,
                            message = message,
                            time = time,
                            imageRes = imageRes,
                            haveSeen = haveSeen,
                            isOnline = isOnline
                        )
                    }
                }
            }
        }
    }
}
