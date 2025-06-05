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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.projectse104.domain.model.Location
import kotlinx.coroutines.delay
@Composable
fun AddNewOfferScreen4(
    navController: NavController,
    userId: String,
    time: String,
    departureLocationId: String,
    toLocationId: String,
    viewModel: AddNewOfferViewModel = hiltViewModel()
) {
    var isRideOfferSent by remember { mutableStateOf(false) }
    var rideOfferCreated by remember { mutableStateOf(false) }
    val addRideOfferState by viewModel.addRideOfferState.collectAsStateWithLifecycle()
    var distance by remember { mutableStateOf("Đang tính khoảng cách...") }
    var departureLocationName by remember { mutableStateOf("") }
    var toLocationName by remember { mutableStateOf("") }

    // Lấy locationList từ ViewModel
    val locationListState by viewModel.locationListState.collectAsStateWithLifecycle()
    LaunchedEffect(locationListState) {
        when (locationListState) {
            is Response.Success -> {
                val locations = (locationListState as Response.Success<List<Location>>).data
                departureLocationName = locations?.find { it.id == departureLocationId }?.name ?: ""
                toLocationName = locations?.find { it.id == toLocationId }?.name ?: ""
                Log.d("AddNewOfferScreen4", "Departure: $departureLocationName, Destination: $toLocationName")
            }
            else -> {}
        }
    }

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

    // Tạo RideOffer
    LaunchedEffect(parsedDate, requestTimeDate, departureLocationName, toLocationName) {
        if (!isRideOfferSent &&
            parsedDate != null &&
            requestTimeDate != null &&
            departureLocationName.isNotBlank() &&
            toLocationName.isNotBlank()
        ) {
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
            isRideOfferSent = true
        }
    }

    // Xử lý kết quả tạo RideOffer
    LaunchedEffect(addRideOfferState) {
        when (addRideOfferState) {
            is Response.Success -> {
                Log.d("AddNewOfferScreen4", "RideOffer created successfully")
                rideOfferCreated = true
            }
            is Response.Failure -> {
                Log.e("AddNewOfferScreen4", "Failed to create RideOffer: ${(addRideOfferState as Response.Failure).e?.message}")
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackArrowWithText(navController, "Add new offer")

        Spacer(modifier = Modifier.height(24.dp))

        // Hiển thị bản đồ
        if (departureLocationName.isNotBlank() && toLocationName.isNotBlank()) {
            OsmMapView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clipToBounds(),
                fromLocation = departureLocationName,
                toLocation = toLocationName,
                context = LocalContext.current,
                onDistanceCalculated = { distanceText ->
                    distance = distanceText
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị khoảng cách
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Khoảng cách: ",
                fontSize = 15.sp
            )
            Text(
                text = distance,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (rideOfferCreated) {
            Button(
                onClick = {
                    navController.navigate("add_new_offer_successfully/$userId")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Finish!")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Hiển thị lỗi nếu có
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