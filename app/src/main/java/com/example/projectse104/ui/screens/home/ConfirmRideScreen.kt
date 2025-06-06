package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.Component.*
import com.example.projectse104.ui.screens.home.Component.*
import com.example.projectse104.core.Response // Đảm bảo import Response

@Composable
fun ConfirmRideScreen(
    navController: NavController,
    riderName: String,
    rideID: String,
    userId: String,
    rideNo:String
) {
    val viewModel: ConfirmRideScreenViewModel = hiltViewModel() // Giả sử dùng ConfirmRideScreenViewModel
    var isUpdating by remember { mutableStateOf(false) }
    var updateError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        // Đảm bảo dữ liệu ban đầu được tải nếu cần
        viewModel.fetchRideDetails(rideID, riderName) // Điều chỉnh tham số nếu cần
    }

    // Sử dụng collectAsStateWithLifecycle để theo dõi updateStatusState
    val updateStatusState by viewModel.updateStatusState.collectAsStateWithLifecycle()

    // Theo dõi trạng thái cập nhật với key là updateStatusState
    LaunchedEffect(updateStatusState) {
        when (updateStatusState) {
            is Response.Success -> {
                navController.navigate("booking_successful/$userId")
            }
            is Response.Failure -> {
                updateError = (updateStatusState as Response.Failure).e?.message ?: "Unknown error"
            }
            else -> {} // Không xử lý Response.Loading ở đây, có thể thêm nếu cần
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BackArrowWithText(navController, "Details of Ride No. $rideNo")

        Spacer(modifier = Modifier.height(100.dp))

        // Image for the tick icon in the center
        Image(
            painter = painterResource(id = R.drawable.confirm_ride),
            contentDescription = "Tick Icon",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Information Text with bold parts for userName and rideID
        val text = buildAnnotatedString {
            append("By clicking the YES button, ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(riderName)
            }
            append(" will join you on the upcoming Ride No. ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(rideNo)
            }
            append(". After clicking YES, please contact the driver to discuss further details about the trip. 😄")
        }

        Text(
            text = text,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        if (updateError != null) {
            Text(
                text = updateError!!,
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons (NO and YES)
        YesNoButtons(
            navController,
            yesOnClick = {
                if (!isUpdating) {
                    isUpdating = true
                    viewModel.updateRideOfferStatusAndAddNewRide(rideID, userId) // Gọi phương thức mới
                }
            },
            noOnClick = { navController.navigate("offer_a_ride/$userId") }
        )
    }
}