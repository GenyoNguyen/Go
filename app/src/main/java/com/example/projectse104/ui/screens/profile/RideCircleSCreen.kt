package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.BackArrowWithText
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.R
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.profile.Component.*

@Composable
fun RideCircleScreen(
    navController: NavController,
    userId: String,
    viewModel: RideCircleViewModel = hiltViewModel()
) {
    val favouriteRiderListState by viewModel.favouriteRiderListState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    var ridesTaken: String = "27"
    var ridesGiven: String = "36"
    var trustScore: String = "209"
    var avatarID: Int = R.drawable.avatar_1
    var riders: List<User> = mutableListOf()
    when (val state = favouriteRiderListState) {
        is Response.Success<List<User>> -> {
            riders = state.data ?: emptyList()
            viewModel.disableLoading()
            println("Recieved riders: $riders")
        }

        is Response.Failure -> {
            ToastMessage(
                message = state.e?.message.toString(),
                show = true
            )
        }

        else -> {}
    }

    if (isLoading) {
        ShimmerRideCircleScreen(navController)
    } else {
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
                        favouriteRider(
                            navController,
                            userId,
                            rider.fullName,
                            R.drawable.avatar_2,
                            "conversationId"
                        )
                    }
                }
            }
        }
    }
}
