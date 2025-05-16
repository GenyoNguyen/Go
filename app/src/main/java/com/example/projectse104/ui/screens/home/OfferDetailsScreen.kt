package com.example.projectse104.ui.screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.model.RideOfferWithLocation
import com.example.projectse104.domain.model.RideOfferWithLocationRider
import com.example.projectse104.domain.model.RideWithRideOfferWithLocation
import com.example.projectse104.domain.model.RideWithUserWithLocation
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.home.Component.OfferDetails
import com.example.projectse104.ui.screens.home.Component.PassengerItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OfferDetailsScreen(
    navController: NavController,
    userId: String,
    rideNo: String,
    viewModel: OfferDetailsViewModel = hiltViewModel()

) {
    val offerState = viewModel.offerState.collectAsStateWithLifecycle()
    val userState by viewModel.user.collectAsStateWithLifecycle()
    var mapImageId = R.drawable.small_map_image
    var requests: List<List<Any>> = listOf(
        listOf(R.drawable.avatar_1, "Nguyễn Hữu Dũng"),
        listOf(R.drawable.avatar_2, "Độ PC")
    )
    var user:User? = null
    var isLoading = true
    var showErrorToast = false
    var errorMessage = ""
    var rideOffer: RideOfferWithLocationRider? = null
    // Handle userState
    when (val state = userState) {
        is Response.Success<User> -> {
            user = state.data//?.fullName.toString().split(" ").last()
        }
        is Response.Failure -> {
            errorMessage = "Không thể tải thông tin người dùng. Vui lòng thử lại!"
            showErrorToast = true
        }
        else -> {} // Loading or initial state
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
        else -> {} // Loading or initial state
    }

    // Determine loading state: only show shimmer if either state is still loading
    isLoading = userState is Response.Loading || offerState.value is Response.Loading

    // Show toast for errors
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
        ) {
            BackArrowWithText(navController, "Details of Ride No. ${rideOffer?.rideOffer?.rideCode.toString()}")
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
            OfferDetails(
                estimatedDeparture = rideOffer?.rideOffer?.estimatedDepartTime.toString(),
                fromLocation = rideOffer?.startLocation.toString(),
                toLocation = rideOffer?.endLocation.toString(),
                riderName = user?.fullName.toString().split(" ").last(),
                riderUserId = user?.userCode.toString(),
                cost = rideOffer?.rideOffer?.coinCost.toString(),
                status = rideOffer?.rideOffer?.status.toString()
            )

            Spacer(modifier = Modifier.height(16.dp))
            for (request in requests) {
                var avatarResId: Int = when (val value = request[0]) {
                    is Int -> value
                    is String -> value.toIntOrNull()
                        ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                    else -> 0 // Nếu không phải Int hoặc String, trả về 0
                }
                var passengerName: String = request[1].toString()
                PassengerItem(navController, avatarResId, passengerName, {
                    navController.navigate("confirm_request/$passengerName/$rideNo/$userId")
                })
            }
        }
    }
}
