package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.TextStyle

@Composable
fun FindARideScreen(navController: NavController, userName: String) {
    var userFullName="Nguyễn Xuân Phúc"
    var userID="100000299"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        var rides:List<List<Any>> = listOf(
            listOf("0054752", "29 Nov, 1:20 pm", "Dĩ An", "Quận 1",R.drawable.avatar_1,"Nguyễn Hữu Dũng","10000512","113"),
            listOf("0054753","30 Nov, 2:00 pm", "HCM", "Quận 5",R.drawable.avatar_2,"Độ PC","10000666","36")
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF8FC79A)), // Màu header
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Header: Home + Hi Phúc
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Home",
                        fontSize = 30.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.header_home),
                        contentDescription = "Home Icon",
                        modifier = Modifier.size(30.dp)
                    )
                }

                Text(
                    text = "Hi, $userName 👋",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }


        // Navigation Buttons (Rides, Find a Ride, Offer a Ride)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
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
                    onClick = { /* Stay on Find a Ride Screen */ },
                    modifier = Modifier,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F8FE))
                ) {
                    Text(text = "Find a Ride", color = Color(0xFF186FF0))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { navController.navigate("offer_a_ride/$userName")
                    },
                    modifier = Modifier,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "Offer a Ride", color = Color(0xFFBFBFBF))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            SearchBar()
        }

        Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())
        Column(modifier=Modifier.padding(horizontal = 16.dp)) {
            // Ride Items List
            for (ride in rides) {
                var rideNo: String = ride[0].toString()
                var estimatedDeparture: String = ride[1].toString()
                var fromLocation: String = ride[2].toString()
                var toLocation: String = ride[3].toString()
                var avatarResId: Int = when (val value = ride[4]) {
                    is Int -> value
                    is String -> value.toIntOrNull()
                        ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                    else -> 0 // Nếu không phải Int hoặc String, trả về 0
                }
                var riderName: String = ride[5].toString()
                var riderId: String = ride[6].toString()
                var coinsEarned: String = ride[7].toString()
                RideItem(
                    navController,
                    rideNo,
                    estimatedDeparture,
                    fromLocation,
                    toLocation,
                    avatarResId,
                    "ride_details",
                    userName,
                    userFullName,
                    userID,
                    riderName,
                    riderId,
                    coinsEarned
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar(navController,userName,1)
    }
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) } // Quản lý trạng thái nhập văn bản

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = {
            Text(
                "Where do you want to go today?",
                fontSize = 16.sp,
                color = Color.Gray,
                lineHeight = 20.sp // Giúp văn bản không bị cắt
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp) // Tăng nhẹ chiều cao để có thêm không gian hiển thị
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFEFF8F2)),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp // Đảm bảo dòng chữ không bị cắt
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFEFF8F2),
            unfocusedContainerColor = Color(0xFFEFF8F2),
            disabledContainerColor = Color(0xFFEFF8F2),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true // Giữ văn bản trên một dòng duy nhất
    )
}

