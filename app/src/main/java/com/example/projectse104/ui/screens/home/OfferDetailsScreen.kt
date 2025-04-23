package com.example.projectse104.ui.screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.RideOffer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.projectse104.ui.screens.home.Component.*

@Composable
fun OfferDetailsScreen(navController: NavController,
                       userId:String,
                       rideNo: String,
) {
    var mapImageId=R.drawable.small_map_image
    var estimatedDeparture: String="29/11/2025 1:20"
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val date: Date? = format.parse(estimatedDeparture)
    var fromLocation: String="KTX"
    var toLocation: String="UIT"
    var riderName: String="Nguyễn Xuân Phúc"
    var riderUserId: String=userId
    var cost: String="43"
    var requests:List<List<Any>> = listOf(
        listOf(R.drawable.avatar_1,"Nguyễn Hữu Dũng"),
        listOf(R.drawable.avatar_2,"Độ PC")
    )
    var isLoading:Boolean=true
    var loadingFailed:Boolean=false
    val state: Response<RideOffer> = Response.Success(RideOffer(id=rideNo, userId = userId, estimatedDepartTime = date,
        startLocation = fromLocation, endLocation = toLocation, coinCost = cost.toIntOrNull()?:0,status="",
        ))
    when(state){
        is Response.Success<RideOffer> -> {
            estimatedDeparture=state.data?.estimatedDepartTime.toString()
            fromLocation=state.data?.startLocation.toString()
            toLocation=state.data?.endLocation.toString()
            cost=state.data?.coinCost.toString()
            isLoading=false
            loadingFailed=false
        }
        is Response.Loading -> {
            isLoading=true
        }
        else -> {
            loadingFailed=true
        }
    }
    ToastMessage(
        message = "Không thể tải dữ liệu. Vui lòng thử lại!",
        show = loadingFailed
    )
    if(isLoading) {
        ShimmerRideDetailsScreen(navController,false)
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            BackArrowWithText(navController, "Details of Ride No. $rideNo")
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
                estimatedDeparture = estimatedDeparture,
                fromLocation = fromLocation,
                toLocation = toLocation,
                riderName = riderName,
                riderUserId = riderUserId,
                cost = cost
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
