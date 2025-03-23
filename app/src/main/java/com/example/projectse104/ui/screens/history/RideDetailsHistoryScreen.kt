package com.example.projectse104.ui.screens.history


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
import com.example.projectse104.ui.screens.home.BottomNavigationBar

@Composable
fun RideDetailsHistoryScreen(
    navController: NavController,
    userName:String,
    rideNo: String,
    estimatedDeparture: String,
    fromLocation: String,
    toLocation: String,
    riderName: String,
    riderUserId: String,
    passengerName: String,
    passengerUserId: String,
    cost: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp), // Thêm padding để căn chỉnh
            verticalAlignment = Alignment.CenterVertically, // Căn giữa theo chiều dọc
            horizontalArrangement = Arrangement.Start // Căn trái để mũi tên ở góc trái
        ) {
            // Mũi tên quay lại
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "Details of Ride No. $rideNo", // Sử dụng tham số rideNo
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Căn giữa theo chiều ngang
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp) // Căn giữa hoàn toàn
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Header with tabs (Overview, Rating)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .width(180.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
                contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
            ) {
                Text(
                    text = "Overview",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
                )
            }

            Button(
                onClick = {navController.navigate("ride_details_rating/$userName/$rideNo/$riderName/$riderUserId/$fromLocation/$toLocation")},
                modifier = Modifier
                    .width(180.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDCF8EA)),
                contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
            ) {
                Text(
                    text = "Rating",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Map image (Use the uploaded map image here)
        Image(
            painter = painterResource(id = R.drawable.map_image), // Thay thế bằng hình ảnh bản đồ thực tế
            contentDescription = "Map Image",
            modifier = Modifier.fillMaxWidth(), // Ảnh sẽ chiếm toàn bộ chiều rộng của container
            contentScale = ContentScale.Crop // Cắt bớt ảnh nếu cần thiết
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Details Section
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Time: $estimatedDeparture", // Sử dụng tham số estimatedDeparture
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Route: From $fromLocation to $toLocation", // Sử dụng tham số fromLocation và toLocation
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Rider: ",
                    fontSize = 15.sp
                )
                Text(
                    text = riderName, // Sử dụng tham số riderName
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "- (UserID: $riderUserId)", // Sử dụng tham số riderUserId
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(
                    text = "Status: ",
                    fontSize = 15.sp
                )
                Text(
                    text = "Success", // Trạng thái cố định, bạn có thể thay đổi nếu cần
                    fontSize = 15.sp,
                    color = Color(0xFF35B82A)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "Passenger Information: ",
                    fontSize = 15.sp
                )
                Text(
                    text = passengerName, // Sử dụng tham số passengerName
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "- (UserID: $passengerUserId)", // Sử dụng tham số passengerUserId
                    fontSize = 15.sp,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(
                    text = "Cost: $cost ", // Sử dụng tham số cost
                    fontSize = 15.sp,
                )
                Text(
                    text = "Ké Coins",
                    fontSize = 15.sp,
                    color = Color.Yellow
                )
            }
        }
    }
}

