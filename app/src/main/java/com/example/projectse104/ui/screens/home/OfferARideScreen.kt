package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.example.projectse104.*


@Composable
fun OfferARideScreen(navController: NavController, userId: String) {
    var userName="Phúc"
    var rides:List<List<Any>> = listOf(
        listOf("0054752", "29 Nov, 1:20 pm", "Dĩ An", "Quận 1",R.drawable.avatar_1),
        listOf("0054753","30 Nov, 2:00 pm", "HCM", "Quận 5",R.drawable.avatar_1)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HomeHeader(userName)


        // Spacer between header and main content
        Spacer(modifier = Modifier.height(0.dp))

        // Main content (rides and offers)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopNavBar(navController,userId,3)


            Spacer(modifier = Modifier.height(16.dp))

            // Call the RideItem composable here
            for (ride in rides) {
                var rideNo:String=ride[0].toString()
                var estimatedDeparture:String=ride[1].toString()
                var fromLocation:String=ride[2].toString()
                var toLocation:String=ride[3].toString()
                var avatarResId: Int = when (val value = ride[4]) {
                    is Int -> value
                    is String -> value.toIntOrNull() ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                    else -> 0 // Nếu không phải Int hoặc String, trả về 0
                }
                RideItem(
                    navController=navController,
                    rideNo=rideNo,
                    estimatedDeparture=estimatedDeparture,
                    fromLocation=fromLocation,
                    toLocation=toLocation,
                    avatarResId=avatarResId,
                    route="offer_details",
                    userId=userId,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        AddNewOffer(navController,userId)


        // Bottom navigation bar (Updated to NavigationBar for Material3)
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController,userId,1)
    }
}
