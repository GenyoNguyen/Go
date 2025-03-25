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

@Composable
fun RideDetailsScreen(
    navController: NavController,
    userId:String,
    rideNo: String,
    estimatedDeparture: String,
    fromLocation: String,
    toLocation: String,
    riderName: String,
    riderUserId: String,
    passengerName: String,
    passengerUserId: String,
    cost: String,
    addGoButton:String
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BackArrowWithText(navController,"Details of Ride No. $rideNo")


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
                    text = "Not Started", // Trạng thái cố định, bạn có thể thay đổi nếu cần
                    fontSize = 15.sp,
                    color = Color.Red
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

            Spacer(modifier = Modifier.height(16.dp))
            if(addGoButton=="yes") {
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

