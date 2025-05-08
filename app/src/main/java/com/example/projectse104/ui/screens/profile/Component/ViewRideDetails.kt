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
fun ViewRideDetails(ridesTaken:String,
                    ridesGiven:String,
                    trustScore:String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp), // Thêm padding để căn chỉnh
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically// Căn trái để mũi tên ở góc trái
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = ridesTaken,
                fontSize = 24.sp,
                color = Color(0xFF242760),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Rides taken",
                fontSize = 18.sp,
                color = Color(0xFF544C44),
                textAlign = TextAlign.Center
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = ridesGiven,
                fontSize = 24.sp,
                color = Color(0xFF242760),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Rides given",
                fontSize = 18.sp,
                color = Color(0xFF544C44),
                textAlign = TextAlign.Center
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = trustScore,
                fontSize = 24.sp,
                color = Color(0xFF242760),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Trust Score",
                fontSize = 18.sp,
                color = Color(0xFF544C44),
                textAlign = TextAlign.Center
            )
        }

    }
}