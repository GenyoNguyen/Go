package com.example.projectse104.ui.screens.chat.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projectse104.R

@Composable
fun ChatItem(
    navController: NavController, // Thêm navController vào để điều hướng
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
                // Navigate to the chat details screen and pass data
                navController.navigate("chat_details/$userId/$otherId")
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
                    .height(60.dp)
                    .width(65.dp)
            ) {
                AsyncImage(
                    model = profilePicUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                // Green dot indicating online status
                if (isOnline) {
                    Image(
                        painter = painterResource(id = R.drawable.online_dot), // Replace with the green dot image resource
                        contentDescription = "Online Status",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(12.dp)
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
                    text = otherName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = message,
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = if (haveSeen) FontWeight.Normal else FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "($time)",
                        fontSize = 13.sp,
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