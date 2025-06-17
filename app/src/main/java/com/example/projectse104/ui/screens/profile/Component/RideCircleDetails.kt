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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projectse104.R
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.rememberShimmer


@Composable
fun RideCircleDetails(
    avatarURL: String?,
    trustScore: String,
    ridesTaken: String,
    ridesGiven: String
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)) {
        Text(
            text = "Your trust score",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
    Spacer(modifier = Modifier.height(40.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = avatarURL,
            contentDescription = "Profile Avatar",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(2.dp, Color(0xFFE0E0E0), CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFDCF8EA))
                .border(
                    1.dp,
                    Color(0XFF7CCFA7),
                    RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = "Trust score: $trustScore",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0XFF7CCFA7)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Total Rides Taken: $ridesTaken",
                fontSize = 18.sp,
                color = Color(0xFF094DE0),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Total Rides Given: $ridesGiven",
                fontSize = 18.sp,
                color = Color(0xFFF4B3B3),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}