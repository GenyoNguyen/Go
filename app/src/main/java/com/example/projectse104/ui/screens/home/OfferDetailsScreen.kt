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

@Composable
fun OfferDetailsScreen(navController: NavController,
                       userName:String,
                       rideNo: String,
                       estimatedDeparture: String,
                       fromLocation: String,
                       toLocation: String,
                       riderName: String,
                       riderUserId: String,
                       cost: String
) {
    var requests:List<List<Any>> = listOf(
        listOf(R.drawable.avatar_1,"Nguyễn Hữu Dũng"),
        listOf(R.drawable.avatar_2,"Độ PC")
    )
    //Để tránh lỗi hiểu value/value thành 2 đặc trưng
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
                text = "Details of Ride No. $rideNo",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Căn giữa theo chiều ngang
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp) // Căn giữa hoàn toàn
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Map image (Use the uploaded map image here)
        Image(
            painter = painterResource(id = R.drawable.small_map_image), // Thay thế bằng hình ảnh bản đồ thực tế
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
                text = "$estimatedDeparture",
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Route: From $fromLocation to $toLocation",
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Rider: ",
                    fontSize = 15.sp
                )
                Text(
                    text = "$riderName",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "- (UserID: $riderUserId)",
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
                    text = "Success",
                    fontSize = 15.sp,
                    color = Color.Green
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Passenger Information: ???",
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Cost: $cost ",
                    fontSize = 15.sp,
                )
                Text(
                    text = "Ké Coins",
                    fontSize = 15.sp,
                    color = Color.Yellow
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Potential Passengers Section
            // Potential Passengers Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Potential Passengers",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa chữ và đường kẻ
                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .weight(1f) // Chiếm toàn bộ không gian còn lại của Row để kéo dài đường kẻ
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            for (request in requests) {
                var avatarResId: Int = when (val value = request[0]) {
                    is Int -> value
                    is String -> value.toIntOrNull() ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                    else -> 0 // Nếu không phải Int hoặc String, trả về 0
                }
                var passengerName: String = request[1].toString()
                PassengerItem(navController, avatarResId, passengerName, {
                    navController.navigate("confirm_request/$passengerName/$rideNo/$userName")
                })
            }
        }
    }
}
@Composable
fun PassengerItem(
    navController: NavController,
    avatarResId: Int,
    passengerName: String,
    onAcceptClick: () -> Unit // Hành động khi nhấn "Accept"
) {
    Column(
        modifier = Modifier.fillMaxWidth() // Đảm bảo column chiếm hết chiều rộng
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Căn chỉnh và thêm khoảng cách giữa các dòng
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Avatar
            Image(
                painter = painterResource(id = avatarResId), // Avatar người dùng
                contentDescription = "Passenger Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(25.dp)) // Bo tròn ảnh avatar
            )

            // Tên hành khách
            Text(
                text = passengerName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            // Nút Accept
            Button(
                onClick = { onAcceptClick() }, // Xử lý khi nhấn Accept
                modifier = Modifier
                    .width(100.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Accept",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
                )
            }
        }

        // Đường đứt nét ở cuối
        Divider(
            color = Color(0xFF8FC79A),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth() // Đảm bảo đường đứt nét dài bằng container
                .padding(top = 8.dp), // Khoảng cách giữa phần trên và đường đứt nét
        )
    }
}
