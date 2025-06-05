package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.RideItem
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.home.Component.HomeHeader
import com.example.projectse104.ui.screens.home.Component.SearchBar
import com.example.projectse104.ui.screens.home.Component.ShimmerHomeScreen
import com.example.projectse104.ui.screens.home.Component.TopNavBar
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projectse104.core.toCustomString
import com.example.projectse104.domain.model.RideOfferWithLocation

@Composable
fun FindARideScreen(navController: NavController,
                    userId: String,
                    viewModel: FindARideVIewModel = hiltViewModel()) {
    val rideOfferListState by viewModel.rideOfferListState.collectAsStateWithLifecycle()
    val userState by viewModel.user.collectAsStateWithLifecycle()
    var rides = emptyList<RideOfferWithLocation>()
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
    when (val state = rideOfferListState) {
        is Response.Success<List<RideOfferWithLocation>> -> {
            rides = state.data.orEmpty()
        }

        is Response.Failure -> {
            errorMessage = "Không thể tải danh sách chuyến đi. Vui lòng thử lại!"
            showErrorToast = true
        }

        else -> {} // Loading or initial state
    }

    // Determine loading state: only show shimmer if either state is still loading
    isLoading = userState is Response.Loading || rideOfferListState is Response.Loading

    // Show toast for errors
    if (showErrorToast) {
        ToastMessage(message = errorMessage, show = true)
    }
    if (isLoading) {
        ShimmerHomeScreen(navController, userId, 2, 1, userName)
    } else {

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background), // Thay bằng ID của hình nền trong res/drawable
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                alpha = 0.2f

                // Hình nền sẽ được scale để phủ toàn màn hình
            )
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController, userId, 1)
                },
                containerColor = Color.Transparent, // Đặt containerColor trong suốt để thấy hình nền
                modifier = Modifier.fillMaxSize()
            )
            { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    HomeHeader(userName)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TopNavBar(navController, userId, 2)

                        Spacer(modifier = Modifier.height(16.dp))

                        SearchBar()

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f) // nội dung cuộn được
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        for (ride in rides) {
                            val rideNo = ride.rideOffer.rideCode
                            val estimatedDeparture =
                                ride.rideOffer.estimatedDepartTime.toCustomString()
                            val fromLocation = ride.startLocation
                            val toLocation = ride.endLocation
                            val avatarResId: Int = R.drawable.avatar_1
                            RideItem(
                                navController = navController,
                                rideNo = rideNo,
                                rideId = ride.rideOffer.id,
                                estimatedDeparture = estimatedDeparture,
                                fromLocation = fromLocation,
                                toLocation = toLocation,
                                avatarResId = avatarResId,
                                route = "ride_details",
                                userId = userId,
                                addGoButton = "yes"
                            )
                        }
                    }
                }
            }
        }
    }
}