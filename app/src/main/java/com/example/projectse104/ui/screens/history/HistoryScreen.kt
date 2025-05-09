package com.example.projectse104.ui.screens.history

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.Header
import com.example.projectse104.Component.RideItem
import com.example.projectse104.Component.ShimmerScreen
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.core.toCustomString
import com.example.projectse104.domain.model.RideWithRideOfferWithLocation


@Composable
fun HistoryScreen(
    navController: NavController,
    userId: String,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val rideListState by viewModel.rideListState.collectAsStateWithLifecycle()

    var isLoading: Boolean = true
    var rides = emptyList<RideWithRideOfferWithLocation>()
    when (val state = rideListState) {
        is Response.Success<List<RideWithRideOfferWithLocation>> -> {
            println("Fetched rides")
            rides = state.data.orEmpty()
            isLoading = false
        }

        is Response.Failure -> {
            println(state.e)
            ToastMessage(
                message = "Không thể tải dữ liệu. Vui lòng thử lại!",
                show = true
            )
        }

        else -> {}
    }

    if (isLoading) {
        ShimmerScreen(navController, userId, 3)
    } else {
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
                        val rideNo = ride.rideOffer.rideCode
                        val estimatedDeparture =
                            ride.ride.departTime.toCustomString()
                        val fromLocation = ride.startLocation
                        val toLocation = ride.endLocation
                        val avatarResId = R.drawable.avatar_1

                        RideItem(
                            navController = navController,
                            rideNo = rideNo,
                            rideId = ride.ride.id,
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





