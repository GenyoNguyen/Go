package com.example.projectse104.ui.screens.chat.Component

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
import com.example.projectse104.Component.*
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
                    .height(60.dp)
                    .width(65.dp)
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
                    text = friendName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = message,
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = if (haveSeen=="yes") FontWeight.Normal else FontWeight.Bold
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