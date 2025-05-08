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
import androidx.navigation.NavController
import com.example.projectse104.BackArrowWithText
import com.example.projectse104.R
import com.example.projectse104.ShimmerRideDetailsScreen
import com.example.projectse104.ToastMessage
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Ride
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.projectse104.ui.screens.history.Component.*

@Composable
fun RideDetailsHistoryScreen(
    navController: NavController,
    userId: String,
    rideNo: String,
) {
    var mapImageId: Int = R.drawable.map_image
    var estimatedDeparture: String = "06/04/2025 14:30"
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val date: Date? = format.parse(estimatedDeparture)
    var fromLocation: String = "Dĩ An"
    var toLocation: String = "Quận 1"
    var riderName: String = "Nguyễn Hữu Dũng"
    var riderUserId: String = "10000512"
    var passengerName: String = "Nguyễn Xuân Phúc"
    var passengerUserId: String = userId
    var cost: String = "113"
    var isLoading: Boolean = true
    var loadingFailed: Boolean = false
    val state: Response<Ride> = Response.Success(
        Ride(
            id = rideNo, rideOfferId = "", passengerId = passengerUserId,
            departTime = date, arriveTime = date, rating = 5f, comment = ""
        )
    )
    when (state) {
        is Response.Success<Ride> -> {
            passengerUserId = state.data?.passengerId.toString()
            riderUserId = "Lmao"
            isLoading = false
            loadingFailed = false
        }

        is Response.Loading -> {
            isLoading = true
        }

        else -> {
            loadingFailed = true
        }
    }
    ToastMessage(
        message = "Không thể tải dữ liệu. Vui lòng thử lại!",
        show = loadingFailed
    )
    if (isLoading) {
        ShimmerRideDetailsScreen(navController, false)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            BackArrowWithText(navController, "Details of Ride No. $rideNo")


            Spacer(modifier = Modifier.height(20.dp))

            // Header with tabs (Overview, Rating)
            OverviewRating(
                navController,
                state = "overview",
                userId,
                rideNo,
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
                estimatedDeparture,
                fromLocation,
                toLocation,
                riderName,
                riderUserId,
                passengerName,
                passengerUserId,
                cost
            )
        }
    }
}

