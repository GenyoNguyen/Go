package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.TextStyle
import com.example.projectse104.*

@Composable
fun FindARideScreen(navController: NavController, userId: String) {
    var userName="Phúc"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        var rides:List<List<Any>> = listOf(
            listOf("0054752", "29 Nov, 1:20 pm", "Dĩ An", "Quận 1",R.drawable.avatar_1),
            listOf("0054753","30 Nov, 2:00 pm", "HCM", "Quận 5",R.drawable.avatar_2)
        )
        HomeHeader(userName)

        // Navigation Buttons (Rides, Find a Ride, Offer a Ride)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopNavBar(navController,userId,2)


            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            SearchBar()
        }

        Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())
        Column(modifier=Modifier.padding(horizontal = 16.dp)) {
            // Ride Items List
            for (ride in rides) {
                var rideNo: String = ride[0].toString()
                var estimatedDeparture: String = ride[1].toString()
                var fromLocation: String = ride[2].toString()
                var toLocation: String = ride[3].toString()
                var avatarResId: Int = when (val value = ride[4]) {
                    is Int -> value
                    is String -> value.toIntOrNull()
                        ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                    else -> 0 // Nếu không phải Int hoặc String, trả về 0
                }
                RideItem(
                    navController,
                    rideNo,
                    estimatedDeparture,
                    fromLocation,
                    toLocation,
                    avatarResId,
                    "ride_details",
                    userId,
                    "yes"
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar(navController,userId,1)
    }
}