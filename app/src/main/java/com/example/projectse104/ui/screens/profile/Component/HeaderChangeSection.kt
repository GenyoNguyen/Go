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
fun HeaderChangeSection(navController: NavController,
                        userAvatarId:Int,
                        userFullName:String,
                        userGmail:String,
                        userId:String){
    Image(
        painter = painterResource(id = userAvatarId), // Đổi lại với icon của bạn
        contentDescription = "Home Icon",
        modifier = Modifier.size(56.dp)
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
            text = "UserID: $userId",
            fontSize = 11.sp,
            color = Color.Gray
        )
    }
    Image(
        painter = painterResource(id = R.drawable.change_icon), // Đổi lại với icon của bạn
        contentDescription = "Home Icon",
        modifier = Modifier.size(24.dp)
            .clickable {navController.navigate("edit_profile/$userId")} // Navigate to page2 when change icon is clicked

    )
}