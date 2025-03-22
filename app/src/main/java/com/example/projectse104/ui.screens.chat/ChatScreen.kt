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
import com.example.projectse104.ui.screens.home.BottomNavigationBar

@Composable
fun ChatScreen(navController: NavController,userName:String) {
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(8.dp)) // Bo tròn 4 góc của header
                .background(Color(0xFF8FC79A)), // Thêm background sau khi clip
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Row cho Home và Hi, {userName}
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start, // Tạo khoảng cách giữa "Home" và "Hi, {userName}"
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Thêm icon ngôi nhà cạnh chữ Home
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Chat",
                        fontSize = 30.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và chữ "Home"
                    Image(
                        painter = painterResource(id = R.drawable.chat_icon_header), // Đổi lại với icon của bạn
                        contentDescription = "Home Icon",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }


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
                    userName,
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
        BottomNavigationBar(navController,userName,2)
    }
}

@Composable
fun ChatItem(
    navController: NavController, // Thêm navController vào để điều hướng
    userName:String,
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
                navController.navigate("chat_details/$userName/$friendName/$isOnline")
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
