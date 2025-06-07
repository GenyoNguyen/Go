package com.example.projectse104.ui.screens.chat.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun MessageItem(
    message: String,
    time: String,
    isSent: Boolean,
    profilePicUrl: String?
) {
    val configuration = LocalConfiguration.current
    val maxWidth = (configuration.screenWidthDp * 0.75).dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = if (isSent) 44.dp else 8.dp, // Thụt vào phải khi là tin nhắn gửi
                end = 8.dp,
                top = 2.dp,
                bottom = 2.dp
            ),
        horizontalArrangement = if (isSent) Arrangement.End else Arrangement.Start
    ) {
        if (!isSent) {
            // Avatar cho tin nhắn nhận
            AsyncImage(
                model = profilePicUrl,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (isSent) Alignment.End else Alignment.Start
        ) {
            // Bubble tin nhắn
            Box(
                modifier = Modifier
                    .widthIn(max = maxWidth)
                    .background(
                        color = if (isSent) {
                            Color(0xFFB2D9AE) // Messenger blue
                        } else {
                            Color(0xFFF0F0F0) // Light gray
                        },
                        shape = RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (isSent) 18.dp else 4.dp,
                            bottomEnd = if (isSent) 4.dp else 18.dp
                        )
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(
                    text = message,
                    color = if (isSent) Color.White else Color.Black,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                )
            }

            // Thời gian
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = time,
                color = Color.Gray,
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }

        if (isSent) {
            // Không cần spacer cho tin nhắn gửi vì đã thụt lề
        } else {
            Spacer(modifier = Modifier.width(36.dp)) // Space cho alignment tin nhắn nhận
        }
    }
}