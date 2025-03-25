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
import androidx.compose.material3.Card
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow


@Composable
fun HomeScreen(navController: NavController, userName: String) {
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
                horizontalArrangement = Arrangement.SpaceBetween, // Tạo khoảng cách giữa "Home" và "Hi, {userName}"
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

                // Text "Hi, {userName}"
                Text(
                    text = "Hi, $userName 👋", // Sử dụng tham số userName
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
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
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = { /* Navigate to Rides Screen */ },
                    modifier = Modifier,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F8FE))
                ) {
                    Text(text = "Rides", color = Color(0xFF186FF0))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {         navController.navigate("find_a_ride/$userName") },
                    modifier = Modifier,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "Find a Ride", color = Color(0xFFBFBFBF))
                }
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
                    route="ride_details",
                    userName=userName,
                    userFullName=userFullName,
                    userID=userID,
                    riderName=riderName,
                    riderId=riderId,
                    coinsEarned=coinsEarned,
                    addGoButton="no"
                )
            }
        }

        // Bottom navigation bar (Updated to NavigationBar for Material3)
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController,userName,1)
    }
}



@Composable
fun BottomNavigationBar(navController: NavController, userName: String, activate: Int) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White // Nền trắng, không nổi bật
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (activate == 1) R.drawable.home_icon_active else R.drawable.home_icon
                    ),
                    contentDescription = "Home",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Unspecified // Không thay màu icon
                )
            },
            selected = activate == 1,
            alwaysShowLabel = false, // ✅ Không hiển thị label
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent, // ✅ Không đổi nền khi chọn
                selectedIconColor = Color.Unspecified,
                unselectedIconColor = Color.Unspecified
            ),
            onClick = { navController.navigate("home/$userName") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (activate == 2) R.drawable.chat_icon_active else R.drawable.chat_icon
                    ),
                    contentDescription = "Chat",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Unspecified
                )
            },
            selected = activate == 2,
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.Unspecified,
                unselectedIconColor = Color.Unspecified
            ),
            onClick = { navController.navigate("chat/$userName") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (activate == 3) R.drawable.history_icon_active else R.drawable.history_icon
                    ),
                    contentDescription = "History",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Unspecified
                )
            },
            selected = activate == 3,
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.Unspecified,
                unselectedIconColor = Color.Unspecified
            ),
            onClick = { navController.navigate("history/$userName") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (activate == 4) R.drawable.profile_icon_active else R.drawable.profile_icon
                    ),
                    contentDescription = "Profile",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Unspecified
                )
            },
            selected = activate == 4,
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.Unspecified,
                unselectedIconColor = Color.Unspecified
            ),
            onClick = { navController.navigate("profile/$userName") }
        )
    }
}


@Composable
fun RideItem(
    navController: NavController,
    rideNo: String="",
    estimatedDeparture: String="",
    fromLocation: String="",
    toLocation: String="",
    avatarResId: Int, // Thêm tham số avatarResId để truyền ảnh
    route: String="", // Thêm tham số route
    userName:String="",
    userFullName:String="",
    userID:String="",
    riderName:String="",
    riderId:String="",
    coinsEarned:String="",
    addGoButton:String=""
) {
    var path:String=if (route=="ride_details") "$route/$userName/$rideNo/$estimatedDeparture/$fromLocation/$toLocation/$riderName/$riderId/$userFullName/$userID/$coinsEarned/$addGoButton"
    else if (route=="ride_details_history") "$route/$userName/$rideNo/$estimatedDeparture/$fromLocation/$toLocation/$riderName/$riderId/$userFullName/$userID/$coinsEarned"
    else "$route/$userName/$rideNo/$estimatedDeparture/$fromLocation/$toLocation/$riderName/$riderId/$coinsEarned"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp) // Độ dày của top border
                .background(Color(0xFF8FC79A)) // Màu của top border
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically, // Căn giữa ảnh theo chiều dọc trong Row
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Ride No. $rideNo", // Sử dụng tham số rideNo
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Căn giữa ảnh theo chiều dọc trong Row
                ) {
                    Text(
                        text = "Estimated Departure",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = estimatedDeparture, // Sử dụng tham số estimatedDeparture
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Căn giữa ảnh theo chiều dọc trong Row
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.path), // Đổi lại với icon của bạn
                        contentDescription = "Path Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "From: $fromLocation - To: $toLocation", // Sử dụng tham số từ and đến
                        color = Color(0xFF8FC79A),
                        fontSize = 15.sp
                    )
                }
            }
            Image(
                painter = painterResource(id = avatarResId), // Sử dụng tham số avatarResId
                contentDescription = "Avatar",
                modifier = Modifier
                    .height(75.dp) // Chiều cao bằng 80% container của nó (phần tử cha)
                    .aspectRatio(1f) // Đảm bảo tỉ lệ 1:1 cho ảnh (vì bạn chỉ định chiều cao)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navController.navigate(path)
            },
            modifier = Modifier
                .width(100.dp)
                .height(30.dp)
                .align(Alignment.End), // Căn chỉnh button về bên phải
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
            contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
        ) {
            Text(
                text = "Details",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}
