package com.example.projectse104.ui.screens.profile.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun HeaderChangeSection(
    navController: NavController,
    userAvatarId: Int,
    userFullName: String,
    userGmail: String,
    userCode: String,
    userId: String,
    profilePicUrl: String?
) {
    AsyncImage(
        model = profilePicUrl,
        contentDescription = "Avatar",
        modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
            .border(2.dp, Color(0xFFE0E0E0), CircleShape) // Thêm viền nhẹ cho avatar
            .clickable { /* Có thể thêm logic để mở image picker nếu cần */ },
        contentScale = ContentScale.Crop
    )
    Column() {
        Text(
            text = userFullName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = userGmail,
            fontSize = 11.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "UserCode: $userCode",
            fontSize = 11.sp,
            color = Color.Gray
        )
    }
    Image(
        painter = painterResource(id = R.drawable.group), // Đổi lại với icon của bạn
        contentDescription = "Home Icon",
        modifier = Modifier
            .size(24.dp)
            .clickable { navController.navigate("edit_profile/$userId") } // Navigate to page2 when change icon is clicked

    )
}