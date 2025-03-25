package com.example.projectse104.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.projectse104.*

@Composable
fun ChatScreen(navController: NavController,userId:String) {
    var conversations:List<List<Any>> = listOf(
        listOf("Nguyễn Hữu Dũng","You: Cảm ơn bạn nhiều !","4 mins",R.drawable.avatar_1,"yes","yes"),
        listOf("Nguyễn Minh Triết","Thanks","43 mins",R.drawable.avatar_2,"yes","no"),
        listOf("Nguyễn Phong Huy", "Lần sau đi nữa nhé!", "4 hours", R.drawable.avatar_1,"no","yes"),
        listOf("Xuân Phúc","You: Oke bro","4 days",R.drawable.avatar_2,"yes","no"),

        )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Header("Chat",R.drawable.chat_icon_header)

        // Chat list section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            for (conversation in conversations) {
                var name:String=conversation[0].toString()
                var message:String=conversation[1].toString()
                var time:String=conversation[2].toString()
                var imageRes: Int = when (val value = conversation[3]) {
                    is Int -> value
                    is String -> value.toIntOrNull() ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                    else -> 0 // Nếu không phải Int hoặc String, trả về 0
                }
                val haveSeen: String = conversation[4].toString()
                val isOnline: String = conversation[5].toString()
                ChatItem(
                    navController,
                    userId,
                    name,
                    message,
                    time,
                    imageRes,
                    haveSeen,
                    isOnline
                )
            }
        }
        // Bottom navigation bar
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController,userId,2)
    }
}