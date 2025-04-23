package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.profile.Component.*

@Composable
fun RideCircleScreen(navController: NavController, userId: String) {
    val ridesTaken = "27"
    val ridesGiven = "36"
    val trustScore = "209"
    val avatarID = R.drawable.avatar_1
    val riders: List<List<Any>> = listOf(
        listOf(R.drawable.avatar_1, "Nguyễn Hữu Dũng", "0001"),
        listOf(R.drawable.avatar_2, "Nguyễn Phong Huy", "0002"),
        listOf(R.drawable.avatar_1, "Trần Văn Bình", "0003"),
        listOf(R.drawable.avatar_2, "Lê Minh Khoa", "0004"),
        listOf(R.drawable.avatar_1, "Ngô Phúc Hậu", "0005"),
        listOf(R.drawable.avatar_2, "Phạm Thảo", "0006"),
        // Thêm vài dòng để kiểm thử scroll
    )
    var isLoading:Boolean=true
    var loadingFailed:Boolean=false
    val state: Response<User> = Response.Success(
        User(id=userId, fullName = "Nguyễn Xuân Phúc",
        email="nguyenxuanphuc010205@gmail.com", profilePic = R.drawable.avatar.toString())
    )
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
        ShimmerRideCircleScreen(navController)
    }
    else {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController, userId, 4)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
            ) {
                BackArrowWithText(navController, "My Ride Circle")

                Spacer(modifier = Modifier.height(20.dp))

                RideCircleDetails(
                    avatarID = avatarID,
                    trustScore = trustScore,
                    ridesTaken = ridesTaken,
                    ridesGiven = ridesGiven
                )

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your favourite rider",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 📌 Danh sách cuộn riêng
                Column(
                    modifier = Modifier
                        .weight(1f) // chiếm phần còn lại
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    for (rider in riders) {
                        val avatarResId = when (val value = rider[0]) {
                            is Int -> value
                            is String -> value.toIntOrNull() ?: 0
                            else -> 0
                        }
                        val accompanyName = rider[1].toString()
                        val conversationId = rider[2].toString()
                        favouriteRider(
                            navController,
                            userId,
                            accompanyName,
                            avatarResId,
                            conversationId
                        )
                    }
                }
            }
        }
    }
}
