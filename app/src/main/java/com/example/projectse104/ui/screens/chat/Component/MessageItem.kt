package com.example.projectse104.ui.screens.chat.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.projectse104.R

@Composable
fun MessageItem(message: String, time: String, isSent: Boolean, profilePicUrl: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isSent) Arrangement.Start else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSent) {
            // Avatar của người gửi
            AsyncImage(
                model = profilePicUrl,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color(0xFFE0E0E0), CircleShape) // Thêm viền nhẹ cho avatar
                    .clickable { /* Có thể thêm logic để mở image picker nếu cần */ },
                contentScale = ContentScale.Crop
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
                        if (isSent) Color(0xFFDCE8F8) else Color(0xFFDCF8EA),
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
                    .align(if (isSent) Alignment.Start else Alignment.End)
            )
        }

        if (isSent) {
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
