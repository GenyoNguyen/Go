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

@Composable
fun ChatDetailsScreen(
    navController: NavController,
    userName:String,
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            // Back Button (Icon)
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            // Avatar
            Image(
                painter = painterResource(id = R.drawable.avatar), // Replace with actual avatar image resource
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

            // User's name and active status
            Column {
                Text(
                    text = friendName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                if(isActive=="yes") {
                    Text(
                        text = "Active now",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

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
@Composable
fun ChatInputField() {
    var message by remember { mutableStateOf("") }  // Lưu tin nhắn

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = message,  // Gán giá trị tin nhắn vào TextField
            onValueChange = { newMessage -> message = newMessage }, // Cập nhật tin nhắn khi thay đổi
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,  // Đảm bảo bàn phím là kiểu văn bản
                imeAction = ImeAction.Send  // Cấu hình hành động bàn phím (nhấn "Enter")
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    sendMessage(message)  // Gửi tin nhắn
                    message = ""  // Reset sau khi gửi
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(Color(0xFFEFF8F2)),
            singleLine = true,
            placeholder = { Text(text = "Type a message", fontSize = 16.sp) },
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                sendMessage(message)  // Gửi tin nhắn khi nhấn vào nút gửi
                message = ""  // Reset sau khi gửi
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Send",
                tint = Color.Blue
            )
        }
    }
}

fun sendMessage(message: String) {
    // Lưu hoặc xử lý tin nhắn
    println("Sent message: $message")  // In ra tin nhắn đã gửi
}

@Composable
fun MessageItem(message: String, time: String, type:String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (type=="receive") Arrangement.Start else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (type=="receive") {
            // If the message is from the other person, show avatar
            Image(
                painter = painterResource(id = R.drawable.avatar), // Replace with the actual avatar resource
                contentDescription = "Profile",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        if (type == "receive") Color(0xFFDCE8F8) else Color(0xFFDCF8EA),
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Text(
                    text = message,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = time,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        if (type=="send") {
            // If the message is from the user, show user's avatar
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.avatar), // Replace with user's avatar resource
                contentDescription = "Profile",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )
        }
    }
    Spacer(modifier=Modifier.height(10.dp))
}
