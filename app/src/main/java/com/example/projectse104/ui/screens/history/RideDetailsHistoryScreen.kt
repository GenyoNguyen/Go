package com.example.projectse104.ui.screens.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.BackArrowWithText
import com.example.projectse104.Component.ShimmerRideDetailsScreen
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.core.Response
import com.example.projectse104.core.toCustomString
import com.example.projectse104.domain.model.RideWithUserWithLocation
import com.example.projectse104.ui.screens.history.Component.OverviewRating
import com.example.projectse104.ui.screens.history.Component.RideContent
import com.example.projectse104.ui.screens.home.Component.OsmMapView
import androidx.compose.foundation.rememberScrollState

@Composable
fun RideDetailsHistoryScreen(
    navController: NavController,
    userId: String,
    rideId: String,
    viewModel: RideDetailsHistoryViewModel = hiltViewModel()
) {
    val rideState = viewModel.rideState.collectAsStateWithLifecycle()
    var isLoading = true
    var ride: RideWithUserWithLocation? = null
    var distance by remember { mutableStateOf("Đang tính khoảng cách...") }
    val scrollState = rememberScrollState()

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
                .verticalScroll(scrollState)
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

            // Map view
            ride?.let {
                OsmMapView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .clipToBounds(),
                    fromLocation = ride.startLocation.toString(),
                    toLocation = ride.endLocation.toString(),
                    context = LocalContext.current,
                    onDistanceCalculated = { distanceText ->
                        distance = distanceText // Cập nhật khoảng cách
                    }
                )
            }

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
                status = ride?.ride?.status.toString(),
                distance = distance
            )
        }
    }
}