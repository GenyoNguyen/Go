package com.example.projectse104.ui.screens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import androidx.compose.material3.Text // For material3 Text
import androidx.compose.ui.draw.clip
import com.example.projectse104.ui.screens.home.BottomNavigationBar
import com.example.projectse104.ui.screens.home.RideItem


@Composable
fun HistoryScreen(navController: NavController,userName:String) {
    var userFullName="Nguyễn Xuân Phúc"
    var userID="100000299"
    var rides:List<List<Any>> = listOf(
        listOf("0054752", "29 Nov, 1:20 pm", "Dĩ An", "Quận 1",R.drawable.avatar_1,"Nguyễn Hữu Dũng","10000512","113"),
        listOf("0054753","30 Nov, 2:00 pm", "HCM", "Quận 5",R.drawable.avatar_2,"Độ PC","10000666","36")
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

            // Row cho Home và Hi, {userName}
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start, // Tạo khoảng cách giữa "Home" và "Hi, {userName}"
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Thêm icon ngôi nhà cạnh chữ Home
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "History",
                        fontSize = 30.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và chữ "Home"
                    Image(
                        painter = painterResource(id = R.drawable.history_header_icon), // Đổi lại với icon của bạn
                        contentDescription = "Home Icon",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }



        // Main content (rides and offers)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            //Liệt kê các chuyến đi từ rides
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
                var riderName:String=ride[5].toString()
                var riderId:String=ride[6].toString()
                var coinsEarned:String=ride[7].toString()
                RideItem(
                    navController=navController,
                    rideNo=rideNo,
                    estimatedDeparture=estimatedDeparture,
                    fromLocation=fromLocation,
                    toLocation=toLocation,
                    avatarResId=avatarResId,
                    route="ride_details_history",
                    userName=userName,
                    userFullName=userFullName,
                    userID=userID,
                    riderName=riderName,
                    riderId=riderId,
                    coinsEarned=coinsEarned,
                )
            }
        }

        // Bottom navigation bar (Updated to NavigationBar for Material3)
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController,userName,3)
    }
}




