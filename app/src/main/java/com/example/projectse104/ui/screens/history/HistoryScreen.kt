package com.example.projectse104.ui.screens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import androidx.compose.material3.Text // For material3 Text
import androidx.compose.ui.draw.clip
import com.example.projectse104.*
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.home.ShimmerHomeScreen


@Composable
fun HistoryScreen(navController: NavController, userId: String) {
    val rides: List<List<Any>> = listOf(
        listOf("0054752", "29 Nov, 1:20 pm", "Dĩ An", "Quận 1", R.drawable.avatar_1),
        listOf("0054753", "30 Nov, 2:00 pm", "HCM", "Quận 5", R.drawable.avatar_2),
        listOf("0054754", "1 Dec, 4:00 pm", "Bình Dương", "Tân Phú", R.drawable.avatar_1),
        listOf("0054755", "3 Dec, 7:30 am", "Thủ Đức", "Quận 3", R.drawable.avatar_2),
        // thêm nhiều dòng để test scroll
    )
    var isLoading:Boolean=true
    var loadingFailed:Boolean=false
    val state: Response<User> = Response.Success(User(id="1111", fullName = "Nguyễn Xuân Phúc"))
    when(state){
        is Response.Success<User> -> {
            isLoading=false
            loadingFailed=false
        }
        is Response.Loading -> {
            isLoading=true
        }
        else -> {
            loadingFailed=true
        }
    }
    ToastMessage(
        message = "Không thể tải dữ liệu. Vui lòng thử lại!",
        show = loadingFailed
    )
    if(isLoading) {
        ShimmerScreen(navController, userId, 3,)
    }
    else{
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, userId, 3)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            Header("History", R.drawable.history_header_icon)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

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
                        route = "ride_details_history",
                        userId = userId
                    )
                }
            }
        }
    }
        }
}





