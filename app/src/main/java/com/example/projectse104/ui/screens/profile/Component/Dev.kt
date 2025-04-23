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
fun Dev(avatarResId: Int, devName: String,devGmail:String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal=16.dp) // Đảm bảo column chiếm hết chiều rộng
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Căn chỉnh và thêm khoảng cách giữa các dòng
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Avatar
            Image(
                painter = painterResource(id = avatarResId), // Avatar người dùng
                contentDescription = "Passenger Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(25.dp)) // Bo tròn ảnh avatar
            )
            Spacer(modifier = Modifier.width(50.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = devName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color=Color(0xFF7CCFA7)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = devGmail,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        // Đường đứt nét ở cuối
        Divider(
            color = Color(0xFF8FC79A),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth() // Đảm bảo đường đứt nét dài bằng container
                .padding(top = 8.dp), // Khoảng cách giữa phần trên và đường đứt nét
        )
    }
}