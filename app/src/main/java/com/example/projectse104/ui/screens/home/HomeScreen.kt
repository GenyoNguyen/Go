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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.RideItem
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.home.Component.HomeHeader
import com.example.projectse104.ui.screens.home.Component.ShimmerHomeScreen
import com.example.projectse104.ui.screens.home.Component.TopNavBar
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.core.toCustomString
import com.example.projectse104.domain.model.RideWithRideOfferWithLocation
@Composable
fun HomeScreen(
    navController: NavController,
    userId: String,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val rideListState by viewModel.rideListState.collectAsStateWithLifecycle()
    val userState by viewModel.user.collectAsStateWithLifecycle()
    var rides = emptyList<RideWithRideOfferWithLocation>()
    var userName = ""
    var isLoading = true
    var showErrorToast = false
    var errorMessage = ""

    // Handle userState
    when (val state = userState) {
        is Response.Success<User> -> {
            userName = state.data?.fullName.toString().split(" ").last()
        }
        is Response.Failure -> {
            errorMessage = "Không thể tải thông tin người dùng. Vui lòng thử lại!"
            showErrorToast = true
        }
        else -> {} // Loading or initial state
    }

    // Handle rideListState
    when (val state = rideListState) {
        is Response.Success<List<RideWithRideOfferWithLocation>> -> {
            rides = state.data.orEmpty()
        }
        is Response.Failure -> {
            errorMessage = "Không thể tải danh sách chuyến đi. Vui lòng thử lại!"
            showErrorToast = true
        }
        else -> {} // Loading or initial state
    }

    // Determine loading state: only show shimmer if either state is still loading
    isLoading = userState is Response.Loading || rideListState is Response.Loading

    // Show toast for errors
    if (showErrorToast) {
        ToastMessage(message = errorMessage, show = true)
    }

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
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (ride in rides) {
                        val rideNo = ride.rideOffer.rideCode
                        val estimatedDeparture = ride.ride.departTime.toCustomString()
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