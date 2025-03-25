package com.example.projectse104.ui.screens.history


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import androidx.compose.ui.layout.ContentScale
import com.example.projectse104.*

@Composable
fun RideDetailsHistoryScreen(
    navController: NavController,
    userId:String,
    rideNo: String,
    estimatedDeparture: String,
    fromLocation: String,
    toLocation: String,
    riderName: String,
    riderUserId: String,
    passengerName: String,
    passengerUserId: String,
    cost: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BackArrowWithText(navController,"Details of Ride No. $rideNo")


        Spacer(modifier = Modifier.height(20.dp))

        // Header with tabs (Overview, Rating)
        OverviewRating(navController,
            state="overview",
            userId,
            rideNo,
            riderName,
            riderUserId,
            fromLocation,
            toLocation)
        Spacer(modifier = Modifier.height(24.dp))

        // Map image (Use the uploaded map image here)
        Image(
            painter = painterResource(id = R.drawable.map_image), // Thay thế bằng hình ảnh bản đồ thực tế
            contentDescription = "Map Image",
            modifier = Modifier.fillMaxWidth(), // Ảnh sẽ chiếm toàn bộ chiều rộng của container
            contentScale = ContentScale.Crop // Cắt bớt ảnh nếu cần thiết
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Details Section
        RideContent(estimatedDeparture,
            fromLocation,
            toLocation,
            riderName,
            riderUserId,
            passengerName,
            passengerUserId,
            cost)
    }
}

