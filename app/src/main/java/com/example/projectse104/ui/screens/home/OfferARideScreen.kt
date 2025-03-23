package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Card
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow


@Composable
fun OfferARideScreen(navController: NavController, userName: String) {
    var userFullName="Nguyễn Xuân Phúc"
    var userID="100000299"
    var rides:List<List<Any>> = listOf(
        listOf("0054752", "29 Nov, 1:20 pm", "Dĩ An", "Quận 1",R.drawable.avatar_1,"113"),
        listOf("0054753","30 Nov, 2:00 pm", "HCM", "Quận 5",R.drawable.avatar_1,"36")
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(8.dp)) // Bo tròn 4 góc của header
                .background(Color(0xFF8FC79A)), // Thêm background sau khi clip
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Row cho Home và Hi userName
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween, // Tạo khoảng cách giữa "Home" và "Hi, userName"
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Thêm icon ngôi nhà cạnh chữ Home
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Home",
                        fontSize = 30.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và chữ "Home"
                    Image(
                        painter = painterResource(id = R.drawable.header_home), // Đổi lại với icon của bạn
                        contentDescription = "Home Icon",
                        modifier = Modifier.size(30.dp)
                    )
                }

                // Text "Hi, userName"
                Text(
                    text = "Hi, $userName 👋",  // Sử dụng userName thay vì "Phúc"
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        // Spacer between header and main content
        Spacer(modifier = Modifier.height(0.dp))

        // Main content (rides and offers)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = { navController.navigate("home/$userName") },
                    modifier = Modifier,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "Rides", color = Color(0xFFBFBFBF))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { navController.navigate("find_a_ride/$userName") },
                    modifier = Modifier,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "Find a Ride", color = Color(0xFFBFBFBF))
                }
                Button(
                    onClick = { /* Navigate to Offer a Ride Screen */ },
                    modifier = Modifier,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F8FE))
                ) {
                    Text(text = "Offer a Ride", color = Color(0xFF186FF0))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Call the RideItem composable here
            for (ride in rides) {
                var rideNo:String=ride[0].toString()
                var estimatedDeparture:String=ride[1].toString()
                var fromLocation:String=ride[2].toString()
                var toLocation:String=ride[3].toString()
                var avatarResId: Int = when (val value = ride[4]) {
                    is Int -> value
                    is String -> value.toIntOrNull() ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                    else -> 0 // Nếu không phải Int hoặc String, trả về 0
                }
                var coinsEarned:String=ride[5].toString()
                RideItem(
                    navController=navController,
                    rideNo=rideNo,
                    estimatedDeparture=estimatedDeparture,
                    fromLocation=fromLocation,
                    toLocation=toLocation,
                    avatarResId=avatarResId,
                    route="offer_details",
                    userName=userName,
                    userFullName ="",
                    userID = "",
                    riderName = userFullName,
                    riderId = userID,
                    coinsEarned=coinsEarned,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
                .clickable {
                    navController.navigate("add_new_offer1/$userName") // Điều hướng đến màn hình tạo chuyến đi mới
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.add_offer_icon), // Ảnh dấu "+"
                contentDescription = "Add Offer Icon",
                modifier = Modifier.size(32.dp) // Kích thước icon
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Add new offer",
                fontSize = 18.sp,
                color = Color(0xFF8FC79A),
                fontWeight = FontWeight.Medium
            )
        }

        // Bottom navigation bar (Updated to NavigationBar for Material3)
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController,userName,1)
    }
}
