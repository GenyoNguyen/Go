package com.example.projectse104.ui.screens.home

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.BackArrowWithText
import com.example.projectse104.Component.ShimmerRideDetailsScreen
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOfferWithLocationRider
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.home.Component.AlertDialog
import com.example.projectse104.ui.screens.home.Component.OfferDetails
import com.example.projectse104.ui.screens.home.Component.OsmMapView

@Composable
fun OfferDetailsScreen(
    navController: NavController,
    userId: String,
    rideOfferId: String,
    viewModel: OfferDetailsViewModel = hiltViewModel()
) {
    val offerState = viewModel.offerState.collectAsStateWithLifecycle()
    val userState by viewModel.user.collectAsStateWithLifecycle()
    var user: User? = null
    var isLoading = true
    var showErrorToast = false
    var errorMessage = ""
    var rideOffer: RideOfferWithLocationRider? = null
    var distance by remember { mutableStateOf("Đang tính khoảng cách...") }

    val openDeleteDialog = remember { mutableStateOf(false) }

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
            BackArrowWithText(
                navController,
                "Details of Ride No. ${rideOffer?.rideOffer?.rideCode.toString()}"
            )
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
            rideOffer?.let {
                OfferDetails(
                    estimatedDeparture = rideOffer.rideOffer.estimatedDepartTime.toString(),
                    fromLocation = rideOffer.startLocation,
                    toLocation = rideOffer.endLocation,
                    riderName = user?.fullName.toString().split(" ").last(),
                    riderUserId = user?.userCode.toString(),
                    cost = rideOffer.rideOffer.coinCost.toString(),
                    status = rideOffer.rideOffer.status,
                    distance = distance // Thêm khoảng cách vào OfferDetails
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(

                onClick = { openDeleteDialog.value = true },
                modifier = Modifier
                    .width(100.dp)
                    .height(30.dp)
                    .align(Alignment.End), // Căn chỉnh button về bên phải
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC030B)),
                contentPadding = PaddingValues(2.dp) // Loại bỏ padding nội dung để text không bị cắt
            ) {
                Text(
                    text = "Delete Offer",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
                )
            }

            when {
                openDeleteDialog.value -> {
                    AlertDialog(
                        dialogTitle = "Confirm Action",
                        dialogText = "Are you sure you want to delete the ride? This action cannot be undone.",
                        icon = Icons.Default.Info,
                        onDismissRequest = {
                            openDeleteDialog.value = false
                        },
                        onConfirmation = {
                            openDeleteDialog.value = false
                            viewModel.deleteRideOffer(rideOfferId)
                            navController.navigate("home/$userId") {
                                popUpTo("home/$userId") { inclusive = true }
                            }
                        }
                    )
                }
            }

        }
    }
}