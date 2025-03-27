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
) {
    var mapImageId:Int=R.drawable.map_image
    var estimatedDeparture: String="29 Nov, 1:20 pm"
    var fromLocation: String="Dĩ An"
    var toLocation: String="Quận 1"
    var riderName: String="Nguyễn Hữu Dũng"
    var riderUserId: String="10000512"
    var passengerName: String="Nguyễn Xuân Phúc"
    var passengerUserId: String=userId
    var cost: String="113"
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
            rideNo,)
        Spacer(modifier = Modifier.height(24.dp))

        // Map image (Use the uploaded map image here)
        Image(
            painter = painterResource(id = mapImageId), // Thay thế bằng hình ảnh bản đồ thực tế
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

