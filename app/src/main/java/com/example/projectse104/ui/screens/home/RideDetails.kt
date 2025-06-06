package com.example.projectse104.ui.screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.BackArrowWithText
import com.example.projectse104.Component.ShimmerRideDetailsScreen
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.Component.rideDetails
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.core.toCustomString
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.RideOfferWithLocation
import com.example.projectse104.domain.model.RideOfferWithLocationRider
import com.example.projectse104.domain.model.RideWithUserWithLocation
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.history.Component.RideContent
import com.example.projectse104.ui.screens.home.Component.OsmMapView
import com.example.projectse104.ui.screens.home.RideDetailsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun RideDetailsScreen(
    navController: NavController,
    userId: String,
    rideNo: String,
    addGoButton: String,
) {
    var distance by remember { mutableStateOf("Đang tính khoảng cách...") }

    if (addGoButton=="no") {
        val viewModel: RideDetailsViewModel = hiltViewModel()
        val rideState = viewModel.rideState.collectAsStateWithLifecycle()
        var mapImageID: Int = R.drawable.map_image

        var isLoading: Boolean = true
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
            ShimmerRideDetailsScreen(navController, true)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()) // Thêm cuộn dọc

            ) {
                BackArrowWithText(navController, "Details of Ride No. ${ride?.rideOffer?.rideCode}")


                Spacer(modifier = Modifier.height(24.dp))

                // Map image (Use the uploaded map image here)
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
                    status=ride?.ride?.status.toString(),
                    distance=distance
                )
                val riderName = ride?.rider?.fullName.toString()

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    else if (addGoButton=="yes") {
        val viewModel: OfferDetailsViewModel = hiltViewModel()
        val offerState = viewModel.offerState.collectAsStateWithLifecycle()
        val userState by viewModel.user.collectAsStateWithLifecycle()
        var mapImageId = R.drawable.map_image
        var user: User? = null
        var isLoading = true
        var showErrorToast = false
        var errorMessage = ""
        var distance by remember { mutableStateOf("Đang tính khoảng cách...") }
        var rideOffer: RideOfferWithLocationRider? = null
        // Handle userState
        when (val state = userState) {
            is Response.Success<User> -> {
                user = state.data
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
            ShimmerRideDetailsScreen(navController, true)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()) // Thêm cuộn dọc

            ) {
                BackArrowWithText(navController, "Details of Ride No. ${rideOffer?.rideOffer?.rideCode}")


                Spacer(modifier = Modifier.height(24.dp))

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
                            distance = distanceText
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Details Section
                RideContent(
                    rideOffer?.rideOffer?.estimatedDepartTime.toCustomString(),
                    rideOffer?.startLocation.toString(),
                    rideOffer?.endLocation.toString(),
                    rideOffer?.rider?.fullName.toString(),
                    rideOffer?.rider?.userCode.toString(),
                    user?.fullName.toString(),
                    user?.userCode.toString(),
                    rideOffer?.rideOffer?.coinCost.toString(),
                    status=rideOffer?.rideOffer?.status.toString(),
                    distance = distance
                )
                val riderName = rideOffer?.rider?.fullName.toString()
                Spacer(modifier = Modifier.height(16.dp))
                    Button(

                        onClick = { navController.navigate("confirm_ride/$riderName/$rideNo/$userId/${rideOffer?.rideOffer?.rideCode}") },
                        modifier = Modifier
                            .width(100.dp)
                            .height(30.dp)
                            .align(Alignment.End), // Căn chỉnh button về bên phải
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
                        contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
                    ) {
                        Text(
                            text = "GO!",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
                        )
                    }
            }
        }
    }
}

