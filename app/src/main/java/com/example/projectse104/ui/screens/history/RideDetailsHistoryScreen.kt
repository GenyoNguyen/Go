package com.example.projectse104.ui.screens.history


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.BackArrowWithText
import com.example.projectse104.Component.ShimmerRideDetailsScreen
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.core.toCustomString
import com.example.projectse104.domain.model.RideWithUserWithLocation
import com.example.projectse104.ui.screens.history.Component.OverviewRating
import com.example.projectse104.ui.screens.history.Component.RideContent

@Composable
fun RideDetailsHistoryScreen(
    navController: NavController,
    userId: String,
    rideId: String,
    viewModel: RideDetailsHistoryViewModel = hiltViewModel()
) {
    val rideState = viewModel.rideState.collectAsStateWithLifecycle()

    val mapImageId: Int = R.drawable.map_image
    var isLoading = true
    var ride: RideWithUserWithLocation? = null
    when (val state = rideState.value) {
        is Response.Success -> {
            state.data?.let {
                isLoading = false
                ride = it
            }
        }

        is Response.Loading -> {
            isLoading = true
        }

        else -> {
            ToastMessage(
                message = "Không thể tải dữ liệu. Vui lòng thử lại!",
                show = true
            )
        }
    }

    if (isLoading) {
        ShimmerRideDetailsScreen(navController, false)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            BackArrowWithText(navController, "Details of Ride No. ${ride?.rideOffer?.rideCode}")


            Spacer(modifier = Modifier.height(20.dp))

            // Header with tabs (Overview, Rating)
            OverviewRating(
                navController,
                state = "overview",
                userId,
                rideId,
            )
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
            RideContent(
                ride?.ride?.departTime.toCustomString(),
                ride?.startLocation.toString(),
                ride?.endLocation.toString(),
                ride?.rider?.fullName.toString(),
                ride?.rider?.userCode.toString(),
                ride?.passenger?.fullName.toString(),
                ride?.passenger?.userCode.toString(),
                ride?.rideOffer?.coinCost.toString(),
                ride?.ride?.status.toString()
            )
        }
    }
}

