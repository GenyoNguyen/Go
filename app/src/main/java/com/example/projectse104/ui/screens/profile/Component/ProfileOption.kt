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
fun ProfileOption(navController: NavController,title: String,avatarID:Int,route:String="") {
    Column(modifier = Modifier.fillMaxWidth().clickable{navController.navigate(route)}) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = avatarID), // Icon for navigation
                contentDescription = "Arrow Icon",
                modifier = Modifier.size(20.dp),
                tint=Color.Gray
            )
            Spacer(modifier=Modifier.width((10.dp)))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right), // Icon for navigation
                contentDescription = "Arrow Icon",
                modifier = Modifier.size(20.dp),
                tint=Color.Gray
            )
        }
        Spacer(modifier=Modifier.height(5.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}