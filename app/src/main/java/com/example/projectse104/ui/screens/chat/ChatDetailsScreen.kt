package com.example.projectse104.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Composable
fun ChatDetailsScreen(
    navController: NavController,
    userId:String,
    friendName: String,
    isActive:String
) {
    var messages:List<List<String>> =listOf(
        listOf("Bạn đi đến Quận 1 phải không?","7:52","receive"),
        listOf("Đúng vậy, tớ ở KTX khu B ấy, tòa B4?","7:53","send"),
        listOf("Oh, tớ cũng ở gần đó, tòa B5, có gì 8h tớ khởi hành thì tớ qua rước bạn nhé. Bạn xuống trước đi tớ chuẩn bị qua ấy!","7:53","receive"),
        listOf("Cảm ơn bạn nhiều !","7:54","receive"),
        )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with user's name and status
        ChatHeader(navController,
            name=friendName,
            avatarID = R.drawable.avatar_1,
            isActive="yes")

        Spacer(modifier = Modifier.height(20.dp))

        // Chat messages
        Column(modifier = Modifier.weight(1f)) {
            for (message in messages){
                var content:String=message[0]
                var time:String=message[1]
                var type:String=message[2]
                MessageItem(
                    message = content,
                    time = time,
                    type=type
                )
            }
        }

        // Message Input Box
        ChatInputField()
    }
}