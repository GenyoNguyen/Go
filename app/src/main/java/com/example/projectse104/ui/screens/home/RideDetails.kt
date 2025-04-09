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
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.profile.ShimmerProfileScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
@Composable
fun RideDetailsScreen(
    navController: NavController,
    userId:String,
    rideNo: String,
    addGoButton:String
    ) {
    var mapImageID:Int=R.drawable.map_image
    var estimatedDeparture: String="06/04/2025 14:30"
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val date: Date? = format.parse(estimatedDeparture)
    var fromLocation: String="Dĩ An"
    var toLocation: String="Quận 1"
    var riderName: String="Nguyễn Hữu Dũng"
    var riderUserId: String="10000512"
    var passengerName: String="Nguyễn Xuân Phúc"
    var passengerUserId: String=userId
    var cost: String="113"
    var isLoading:Boolean=true
    var loadingFailed:Boolean=false
    val state: Response<Ride> = Response.Success(Ride(id=rideNo, rideOfferId = "", passengerId = passengerUserId, driverId = riderUserId,
        departTime = date,arriveTime=date, rating=5f, comment = ""))
    when(state){
        is Response.Success<Ride> -> {
            passengerUserId=state.data?.passengerId.toString()
            riderUserId=state.data?.driverId.toString()
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
        ShimmerRideDetailsScreen(navController,true)
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
                painter = painterResource(id = mapImageID), // Thay thế bằng hình ảnh bản đồ thực tế
                contentDescription = "Map Image",
                modifier = Modifier.fillMaxWidth(), // Ảnh sẽ chiếm toàn bộ chiều rộng của container
                contentScale = ContentScale.Crop // Cắt bớt ảnh nếu cần thiết
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Details Section
            rideDetails(
                estimatedDeparture = estimatedDeparture,
                fromLocation = fromLocation,
                toLocation = toLocation,
                riderName = riderName,
                riderUserId = riderUserId,
                passengerName = passengerName,
                passengerUserId = passengerUserId,
                cost = cost
            )

            Spacer(modifier = Modifier.height(16.dp))
            if (addGoButton == "yes") {
                Button(
                    onClick = { navController.navigate("confirm_ride/$riderName/$rideNo/$userId") },
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

