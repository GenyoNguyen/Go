package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.*

@Composable
fun ProfileScreen(navController: NavController, userId: String) {
    var userFullName: String = "Nguyễn Xuân Phúc"
    var userGmail: String = "nguyenxuanphuc010205@gmail.com"
    var userID: String = "10000299"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header section with profile name and icon
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(8.dp)) // Bo tròn 4 góc của header
                .background(Color(0xFF8FC79A)), // Thêm background sau khi clip
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(45.dp))                // Row cho Home và Hi, {userName}
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
                        text = "My Profile",
                        fontSize = 30.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và chữ "Home"
                    Image(
                        painter = painterResource(id = R.drawable.user_icon), // Đổi lại với icon của bạn
                        contentDescription = "Home Icon",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(45.dp))                // Row cho Home và Hi, {userName}

        }
        Column(modifier = Modifier.offset(y = -70.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp)) // Bo tròn 4 góc của header
                    .background(Color.White)
                    .border(
                        1.dp,
                        Color.LightGray,
                        RoundedCornerShape(8.dp)

                    )
                    .clickable {navController.navigate("profile_view/$userID")} // Navigate to page1 when the column is clicked
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround // Thêm background sau khi clip
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.avatar), // Đổi lại với icon của bạn
                        contentDescription = "Home Icon",
                        modifier = Modifier.size(70.dp)
                    )
                    Column() {
                        Text(
                            text = userFullName,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = userGmail,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "UserID: $userID",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.change_icon), // Đổi lại với icon của bạn
                        contentDescription = "Home Icon",
                        modifier = Modifier.size(30.dp)
                            .clickable {navController.navigate("edit_profile/$userId")} // Navigate to page2 when change icon is clicked

                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Spacer between header and options
            // List of options
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                ProfileOption(navController=navController,title = "Saved Locations", avatarID = R.drawable.profile_icon_1,"saved_locations/$userId")
                ProfileOption(navController=navController,title = "Promotions & Rewards", avatarID = R.drawable.profile_icon_2,"promotion_rewards/$userId")
                ProfileOption(navController=navController,title = "My Ride Circle", avatarID = R.drawable.profile_icon_3,"ride_circle/$userId")
                ProfileOption(navController=navController,title = "Help & Support", avatarID = R.drawable.profile_icon_4,"help_support/$userId")
                ProfileOption(navController=navController,title = "Sign out", avatarID = R.drawable.profile_icon_5)
            }

            // Bottom navigation bar
        }
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController, userId, 4)

    }
}

@Composable
fun ProfileOption(navController: NavController,title: String,avatarID:Int,route:String="") {
    Column(modifier = Modifier.fillMaxWidth().clickable{navController.navigate(route)}) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = avatarID), // Icon for navigation
                contentDescription = "Arrow Icon",
                modifier = Modifier.size(20.dp),
                tint=Color.Gray
            )
            Spacer(modifier=Modifier.width((10.dp)))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right), // Icon for navigation
                contentDescription = "Arrow Icon",
                modifier = Modifier.size(20.dp),
                tint=Color.Gray
            )
        }
        Spacer(modifier=Modifier.height(5.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}

