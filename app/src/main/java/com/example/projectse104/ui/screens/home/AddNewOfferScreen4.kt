package com.example.projectse104.ui.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.Component.*
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.ui.screens.home.Component.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@Composable
fun AddNewOfferScreen4(
    navController: NavController,
    userId: String,
    time: String,
    departureLocationId: String,
    toLocationId: String,
    viewModel: AddNewOfferViewModel = hiltViewModel()
) {
    // Theo dõi trạng thái tạo RideOffer
    val addRideOfferState by viewModel.addRideOfferState.collectAsStateWithLifecycle()

    // Chuẩn hóa và parse time
    val normalizedTime = if (time.length == 16) "$time:00" else time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val parsedDate: Date? = try {
        dateFormat.parse(normalizedTime)
    } catch (e: Exception) {
        Log.e("AddNewOfferScreen4", "Failed to parse time: $time, error: ${e.message}")
        null
    }

    // Lấy thời gian hiện tại (ISO 8601)
    val currentTimeMillis = System.currentTimeMillis()
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = currentTimeMillis
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("Asia/Bangkok")
    val isoTime = formatter.format(calendar.time)
    val requestTimeDate: Date? = try {
        formatter.parse(isoTime)
    } catch (e: Exception) {
        Log.e("AddNewOfferScreen4", "Failed to parse request time: $isoTime, error: ${e.message}")
        null
    }

    LaunchedEffect(true) {
        // Delay for 2 seconds
        kotlinx.coroutines.delay(1000)
        // Tạo RideOffer
        if (parsedDate != null && requestTimeDate != null) {
            val rideOffer = RideOffer(
                id = UUID.randomUUID().toString(),
                userId = userId,
                startLocationId = departureLocationId,
                requestTime = requestTimeDate,
                endLocationId = toLocationId,
                estimatedDepartTime = parsedDate,
                rideCode = UUID.randomUUID().toString().replace("-", "").take(6),
                status = "PENDING",
                coinCost = Random.nextInt(10, 101)
            )
            Log.d("AddNewOfferScreen4", "Creating RideOffer: $rideOffer")
            viewModel.addRideOffer(rideOffer)
        } else {
            Log.e("AddNewOfferScreen4", "Cannot create RideOffer: parsedDate or requestTimeDate is null")
        }
    }

    // Xử lý kết quả tạo RideOffer
    LaunchedEffect(addRideOfferState) {
        when (addRideOfferState) {
            is Response.Success -> {
                Log.d("AddNewOfferScreen4", "RideOffer created successfully")
                navController.navigate("add_new_offer_successfully/$userId")
            }
            is Response.Failure -> {
                Log.e("AddNewOfferScreen4", "Failed to create RideOffer: ${(addRideOfferState as Response.Failure).e?.message}")
                // Có thể hiển thị lỗi cho người dùng nếu cần
            }
            else -> Unit // Không làm gì khi đang loading
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.estimate_icon),
            contentDescription = "Clock Icon",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Estimated Ké Coins to be earned",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        // Hiển thị lỗi nếu có (tùy chọn)
        addRideOfferState?.let { state ->
            if (state is Response.Failure) {
                Text(
                    text = "Error: ${state.e?.message}",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}