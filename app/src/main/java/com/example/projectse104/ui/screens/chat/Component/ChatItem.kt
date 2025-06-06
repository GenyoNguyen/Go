package com.example.projectse104.ui.screens.chat.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projectse104.R

@Composable
fun ChatItem(
    navController: NavController,
    userId: String,
    otherId: String,
    otherName: String,
    message: String,
    time: String,
    imageRes: Int,
    haveSeen: Boolean,
    isOnline: Boolean,
    profilePicUrl: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("chat_details/$userId/$otherId")
            }
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar với online status
            Box(
                modifier = Modifier.size(56.dp)
            ) {
                AsyncImage(
                    model = profilePicUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                // Chấm xanh online status - style Messenger
                if (isOnline) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(18.dp)
                            .offset(x = 2.dp, y = 2.dp)
                            .background(Color.White, CircleShape)
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .background(Color(0xFF42C653), CircleShape)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Nội dung tin nhắn
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Tên người dùng
                Text(
                    text = otherName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,

                )

                Spacer(modifier = Modifier.height(4.dp))

                // Tin nhắn cuối cùng
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = if (haveSeen) Color.Gray else Color.Black,
                    fontWeight = if (haveSeen) FontWeight.Normal else FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
            }

            // Thời gian và trạng thái chưa đọc
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = if (haveSeen) Color.Gray else Color(0xFF0084FF),
                    fontWeight = if (haveSeen) FontWeight.Normal else FontWeight.Medium
                )

                // Chấm thông báo tin nhắn chưa đọc
                if (!haveSeen) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFF0084FF), CircleShape)
                    )
                }
            }
        }

        // Đường phân cách mỏng như Messenger
        Divider(
            color = Color(0xFFE4E6EA),
            thickness = 0.5.dp,
            modifier = Modifier.padding(start = 30.dp) // Bắt đầu từ sau avatar
        )
    }
}