package com.example.projectse104.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
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
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.rememberShimmer
@Composable
fun ChatItem(
    navController: NavController, // Thêm navController vào để điều hướng
    userId:String,
    conversationId:String,
    friendName: String,
    message: String,
    time: String,
    imageRes: Int,
    haveSeen: String,
    isOnline: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Navigate to the chat details screen and pass data
                navController.navigate("chat_details/$userId/$conversationId")
            }
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar with online status
            Box(
                modifier = Modifier
                    .height(75.dp)
                    .width(75.dp)
            ) {
                Image(
                    painter = painterResource(id = imageRes), // Avatar image
                    contentDescription = "Profile",
                    modifier = Modifier
                        .fillMaxSize()  // Ensures the image takes up the full space
                        .clip(RoundedCornerShape(50)) // Makes the avatar circular
                )

                // Green dot indicating online status
                if (isOnline=="yes") {
                    Image(
                        painter = painterResource(id = R.drawable.online_dot), // Replace with the green dot image resource
                        contentDescription = "Online Status",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(15.dp)
                            .offset(x = -5.dp) // Adjust the size of the dot
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Message and time text
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = friendName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = message,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = if (haveSeen=="yes") FontWeight.Normal else FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "($time)",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp) // Border thickness
                .background(Color.Black) // Color of the border
        )

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
fun MessageItem(message: String, time: String, type: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (type == "receive") Arrangement.Start else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (type == "receive") {
            // Avatar của người gửi
            Image(
                painter = painterResource(id = R.drawable.avatar),
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

            // 👇 Canh chỉnh thời gian theo type
            Text(
                text = time,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(if (type == "send") Alignment.Start else Alignment.End)
            )
        }

        if (type == "send") {
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun ChatHeader(navController: NavController,name:String,avatarID:Int,isActive:String){
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
            painter = painterResource(id = avatarID), // Replace with actual avatar image resource
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        // User's name and active status
        Column {
            Text(
                text = name,
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
}

@Composable
fun ShimmerChatDetailsScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 👉 Header giả lập
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.LightGray, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(18.dp)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(12.dp)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 👉 Danh sách tin nhắn shimmer
        Column(
            modifier = Modifier
                .weight(1f)
                .shimmer(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(8) { i ->
                if (i % 2 == 0) {
                    // 👉 Row cho i chẵn (tin nhắn nhận)
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color.LightGray, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Box(
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(20.dp)
                                    .background(Color.LightGray, RoundedCornerShape(12.dp))
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(12.dp)
                                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                            )
                        }
                    }
                } else {
                    // 👉 Row cho i lẻ (tin nhắn gửi)
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(20.dp)
                                    .background(Color.LightGray, RoundedCornerShape(12.dp))
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(12.dp)
                                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color.LightGray, CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        // 👉 Input box shimmer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shimmer(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(0.85f)
                    .background(Color.LightGray, RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.LightGray, CircleShape)
            )
        }
    }
}
