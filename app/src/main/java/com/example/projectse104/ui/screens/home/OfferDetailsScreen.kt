package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.example.projectse104.ui.screens.home.Component.OsmMapView
import com.example.projectse104.Component.BackArrowWithText
import com.example.projectse104.Component.ShimmerRideDetailsScreen
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOfferWithLocationRider
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.home.Component.OfferDetails
import com.example.projectse104.ui.screens.home.Component.PassengerItem

@Composable
fun OfferDetailsScreen(
    navController: NavController,
    userId: String,
    rideNo: String,
    viewModel: OfferDetailsViewModel = hiltViewModel()
) {
    val offerState = viewModel.offerState.collectAsStateWithLifecycle()
    val userState by viewModel.user.collectAsStateWithLifecycle()
    var requests: List<List<Any>> = listOf(
        listOf(R.drawable.avatar_1, "Nguyễn Hữu Dũng"),
        listOf(R.drawable.avatar_2, "Độ PC")
    )
    var user: User? = null
    var isLoading = true
    var showErrorToast = false
    var errorMessage = ""
    var rideOffer: RideOfferWithLocationRider? = null
    var distance by remember { mutableStateOf("Đang tính khoảng cách...") }

    // Handle userState
    when (val state = userState) {
        is Response.Success<User> -> {
            user = state.data
        }
        is Response.Failure -> {
            errorMessage = "Không thể tải thông tin người dùng. Vui lòng thử lại!"
            showErrorToast = true
        }
        else -> {}
    }

    // Handle rideListState
    when (val state = offerState.value) {
        is Response.Success<RideOfferWithLocationRider> -> {
            rideOffer = state.data
        }
        is Response.Failure -> {
            errorMessage = "Không thể tải danh sách chuyến đi. Vui lòng thử lại!"
            showErrorToast = true
        }
        else -> {}
    }

    // Determine loading state
    isLoading = userState is Response.Loading || offerState.value is Response.Loading

    if (showErrorToast) {
        ToastMessage(message = errorMessage, show = true)
    }

    if (isLoading) {
        ShimmerRideDetailsScreen(navController, false)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            BackArrowWithText(navController, "Details of Ride No. ${rideOffer?.rideOffer?.rideCode.toString()}")
            Spacer(modifier = Modifier.height(24.dp))

            // Hiển thị bản đồ
            rideOffer?.let {
                OsmMapView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .clipToBounds(),
                    fromLocation = rideOffer.startLocation.toString(),
                    toLocation = rideOffer.endLocation.toString(),
                    context = LocalContext.current,
                    onDistanceCalculated = { distanceText ->
                        distance = distanceText // Cập nhật khoảng cách
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Details Section
            OfferDetails(
                estimatedDeparture = rideOffer?.rideOffer?.estimatedDepartTime.toString(),
                fromLocation = rideOffer?.startLocation.toString(),
                toLocation = rideOffer?.endLocation.toString(),
                riderName = user?.fullName.toString().split(" ").last(),
                riderUserId = user?.userCode.toString(),
                cost = rideOffer?.rideOffer?.coinCost.toString(),
                status = rideOffer?.rideOffer?.status.toString(),
                distance = distance // Thêm khoảng cách vào OfferDetails
            )

            Spacer(modifier = Modifier.height(16.dp))
            for (request in requests) {
                var avatarResId: Int = when (val value = request[0]) {
                    is Int -> value
                    is String -> value.toIntOrNull() ?: 0
                    else -> 0
                }
                var passengerName: String = request[1].toString()
                PassengerItem(navController, avatarResId, passengerName, {
                    navController.navigate("confirm_request/$passengerName/$rideNo/$userId")
                })
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}