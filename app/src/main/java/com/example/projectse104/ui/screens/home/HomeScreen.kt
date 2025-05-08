package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.RideItem
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.home.Component.HomeHeader
import com.example.projectse104.ui.screens.home.Component.ShimmerHomeScreen
import com.example.projectse104.ui.screens.home.Component.TopNavBar


@Composable
fun HomeScreen(navController: NavController, userId: String) {
    var userName = "Phúc"
    var rides: List<List<Any>> = listOf(
        listOf("0054752", "29 Nov, 1:20 pm", "Dĩ An", "Quận 1", R.drawable.avatar_1),
        listOf("0054753", "30 Nov, 2:00 pm", "HCM", "Quận 5", R.drawable.avatar_2),
        listOf("0054752", "29 Nov, 1:20 pm", "Dĩ An", "Quận 1", R.drawable.avatar_1),
        listOf("0054752", "29 Nov, 1:20 pm", "Dĩ An", "Quận 1", R.drawable.avatar_1),
        listOf("0054752", "29 Nov, 1:20 pm", "Dĩ An", "Quận 1", R.drawable.avatar_1),
        listOf("0054752", "29 Nov, 1:20 pm", "Dĩ An", "Quận 1", R.drawable.avatar_1),
    )
    var isLoading: Boolean = true
    var loadingFailed: Boolean = false
    val state: Response<User> = Response.Success(
        User(
            id = "1111",
            fullName = "Nguyễn Xuân Phúc",
            overallRating = 5.0f,
            coins = 100,
            userCode = "kzdf2",
            vehicleId = "Lmao"
        )
    )
    when (state) {
        is Response.Success<User> -> {
            val fullName = state.data?.fullName?.trim()
            userName = fullName
                ?.split("\\s+".toRegex())
                ?.lastOrNull()
                ?: "Người dùng"
            isLoading = false
            loadingFailed = false
        }

        is Response.Loading -> {
            isLoading = true
        }

        else -> {
            loadingFailed = true
        }
    }
    ToastMessage(
        message = "Không thể tải dữ liệu. Vui lòng thử lại!",
        show = loadingFailed
    )
    if (isLoading) {
        ShimmerHomeScreen(navController, userId, 1, 1)
    } else {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController, userId, 1)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
            ) {
                HomeHeader(userName)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopNavBar(navController, userId, 1)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Đây là phần quan trọng: giúp nội dung scroll được nhưng không che bottom bar
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (ride in rides) {
                        val rideNo = ride[0].toString()
                        val estimatedDeparture = ride[1].toString()
                        val fromLocation = ride[2].toString()
                        val toLocation = ride[3].toString()
                        val avatarResId = when (val value = ride[4]) {
                            is Int -> value
                            is String -> value.toIntOrNull() ?: 0
                            else -> 0
                        }

                        RideItem(
                            navController = navController,
                            rideNo = rideNo,
                            estimatedDeparture = estimatedDeparture,
                            fromLocation = fromLocation,
                            toLocation = toLocation,
                            avatarResId = avatarResId,
                            route = "ride_details",
                            userId = userId,
                            addGoButton = "no"
                        )
                    }
                }
            }
        }
    }
}
