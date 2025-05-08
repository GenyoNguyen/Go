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
import com.example.projectse104.Component.*
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.profile.Component.*

@Composable
fun ProfileScreen(navController: NavController, userId: String) {
    var userFullName: String = "Nguyễn Xuân Phúc"
    var userGmail: String = "nguyenxuanphuc010205@gmail.com"
    var userAvatarId:Int=R.drawable.avatar
    var isLoading:Boolean=true
    var loadingFailed:Boolean=false
    val state: Response<User> = Response.Success(User(id=userId, fullName = "Nguyễn Xuân Phúc",
        email="nguyenxuanphuc010205@gmail.com", profilePic = R.drawable.avatar.toString()))
    when(state){
        is Response.Success<User> -> {
            userFullName=state.data?.fullName.toString()
            userGmail=state.data?.email.toString()
            userAvatarId = state.data?.profilePic?.toIntOrNull() ?: R.drawable.avatar
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
        ShimmerProfileScreen(navController, userId)
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Header section with profile name and icon
            ProfileHeader()
            Column(modifier = Modifier.offset(y = -70.dp)) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp)) // Bo tròn 4 góc của header
                    .background(Color.White)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    .clickable { navController.navigate("profile_view/$userId/yes") } // Navigate to page1 when the column is clicked
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround // Thêm background sau khi clip
                    ) {
                        HeaderChangeSection(
                            navController,
                            userAvatarId,
                            userFullName,
                            userGmail,
                            userId
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
                    ProfileOption(
                        navController = navController,
                        title = "Saved Locations",
                        avatarID = R.drawable.profile_icon_1,
                        "saved_locations/$userId"
                    )
                    ProfileOption(
                        navController = navController,
                        title = "Promotions & Rewards",
                        avatarID = R.drawable.profile_icon_2,
                        "promotion_rewards/$userId"
                    )
                    ProfileOption(
                        navController = navController,
                        title = "My Ride Circle",
                        avatarID = R.drawable.profile_icon_3,
                        "ride_circle/$userId"
                    )
                    ProfileOption(
                        navController = navController,
                        title = "Help & Support",
                        avatarID = R.drawable.profile_icon_4,
                        "help_support/$userId"
                    )
                    ProfileOption(
                        navController = navController,
                        title = "Sign out",
                        avatarID = R.drawable.profile_icon_5,
                        route="sign_in"
                    )
                }

                // Bottom navigation bar
            }
            Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
            BottomNavigationBar(navController, userId, 4)

        }
    }
}



