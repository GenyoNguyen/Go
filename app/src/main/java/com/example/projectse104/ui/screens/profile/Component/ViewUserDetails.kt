package com.example.projectse104.ui.screens.profile.Component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.rememberShimmer
@Composable
fun ViewUserDetails(avatarID:Int,
                    userFullName:String,
                    rating:String,
                    position:String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), // Thêm padding để căn chỉnh
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically// Căn trái để mũi tên ở góc trái
    ) {
        Image(
            painter = painterResource(id = avatarID), // Ensure this is a valid drawable resource
            contentDescription = "Profile Avatar",
            modifier = Modifier
                .size(150.dp)
        )
        Column {
            Text(
                text = userFullName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Active member",
                fontSize = 18.sp,
                color = Color(0xFF7CCFA7)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Rating: $rating/5",
                fontSize = 18.sp,
                color = Color(0xFFBEB204),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(){
                Icon(
                    painter = painterResource(id = R.drawable.profile_view_location), // Icon for navigation
                    contentDescription = "Arrow Icon",
                    modifier = Modifier.size(20.dp),
                    tint=Color(0xFF544C44)
                )
                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = position,
                    fontSize = 18.sp,
                    color = Color(0xFF544C44)
                )
            }
        }
    }
}