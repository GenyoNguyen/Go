package com.example.projectse104.ui.screens.history


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.ShimmerRideDetailsScreen
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideWithPassengerAndRider
import com.example.projectse104.ui.screens.history.Component.OverviewRating
import com.example.projectse104.ui.screens.history.Component.RatingContent
import com.example.projectse104.ui.screens.history.Component.ratingStars

@Composable
fun RideDetailsRatingScreen(
    navController: NavController,
    userId: String,
    rideId: String,
    viewModel: RideDetailsRatingViewModel = hiltViewModel()
) {
    val rideState by viewModel.rideState.collectAsStateWithLifecycle()

    val riderAvatarId: Int = R.drawable.avatar_1

    var isLoading: Boolean = true
    var rideWithPassengerAndRider: RideWithPassengerAndRider? = null
    when (val state = rideState) {
        is Response.Success<RideWithPassengerAndRider> -> {
            isLoading = false
            rideWithPassengerAndRider = state.data
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Mũi tên quay lại
                IconButton(onClick = { navController.navigate("history/$userId") }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "Details of Ride No. ${rideWithPassengerAndRider?.rideOffer?.rideCode}", // Sử
                    // dụng tham số
                    // rideNo
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .offset(x = -15.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OverviewRating(
                navController,
                state = "rating",
                userId,
                rideId,
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Rating and feedback section
            ratingStars(rideWithPassengerAndRider?.ride?.rating?.toInt() ?: 0)


            Spacer(modifier = Modifier.height(20.dp))

            // Rider Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RatingContent(
                    riderAvatarId,
                    trustScore = (rideWithPassengerAndRider?.ride?.rating?.toInt() ?: 0) - 3 * 10,
                    keCoins = rideWithPassengerAndRider?.rideOffer?.coinCost ?: 0,
                    riderName = rideWithPassengerAndRider?.rider?.fullName.toString(),
                    riderUserId = rideWithPassengerAndRider?.rider?.userCode.toString(),
                    content = rideWithPassengerAndRider?.ride?.comment.toString()
                )


                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start // Căn chỉnh từ bên trái
                ) {
                    // Text với phần clickable được xây dựng bằng AnnotatedString
                    Text(
                        text = buildAnnotatedString {
                            append("Want to contact again with ")
                            pushStyle(SpanStyle(fontWeight = FontWeight.Bold)) // In đậm tên tài xế
                            append(rideWithPassengerAndRider?.rider?.fullName) // In đậm tên tài xế
                            pop() // Kết thúc phần in đậm
                            append(", ")
                            pushStyle(SpanStyle(color = Color(0xFF35B82A))) // Đổi màu xanh cho phần "click here"
                            append("click here") // Phần clickable
                            pop() // Đóng phần style
                        },
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("chat_details/${userId}/${rideWithPassengerAndRider?.rider?.id}")
                            }
                    )
                }


            }
        }
    }
}
