package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.BackArrowWithText
import com.example.projectse104.BottomNavigationBar
import com.example.projectse104.R
import com.example.projectse104.ToastMessage
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User

@Composable
fun ProfileViewScreen(navController: NavController, userId: String) {
    var userFullName: String = "Nguyễn Xuân Phúc"
    var rating: String = "4.5"
    var position: String = "Dĩ An, Bình Dương"
    var ridesTaken: String = "27"
    var ridesGiven: String = "36"
    var trustScore: String = "209"
    var avatarID: Int = R.drawable.avatar_1
    var accompanies: List<List<Any>> = listOf(
        listOf(R.drawable.avatar_1, "Nguyễn Hữu Dũng"),
        listOf(R.drawable.avatar_2, "Nguyễn Phong Huy"),
    )
    var isLoading: Boolean = true
    var loadingFailed: Boolean = false
    val state: Response<User> = Response.Success(
        User(
            id = userId,
            fullName = "Nguyễn Xuân Phúc",
            email = "nguyenxuanphuc010205@gmail.com",
            profilePic = R.drawable.avatar.toString(),
            overallRating = 4.5f,
            location = position,
            password = "",
            phoneNumber = "TODO()",
            coins = 0,
            userCode = "TODO()"
        )
    )
    when (state) {
        is Response.Success<User> -> {
            userFullName = state.data?.fullName.toString()
            avatarID = state.data?.profilePic?.toIntOrNull() ?: R.drawable.avatar_1
            rating = state.data?.overallRating.toString()
            position = state.data?.location.toString()
            isLoading = false
            loadingFailed = false
        }

        is Response.Loading -> {
            isLoading = true
        }

        else -> {
            loadingFailed = true
        }
    }
    ToastMessage(
        message = "Không thể tải dữ liệu. Vui lòng thử lại!",
        show = loadingFailed
    )
    if (isLoading) {
        ShimmerProfileViewScreen(navController)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            BackArrowWithText(navController, "Profile")

            Spacer(modifier = Modifier.height(20.dp))

            ViewUserDetails(
                avatarID,
                userFullName,
                rating,
                position
            )
            Spacer(modifier = Modifier.height(20.dp))

            ViewRideDetails(
                ridesTaken,
                ridesGiven,
                trustScore
            )

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent activity",
                    fontSize = 20.sp,
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
            Spacer(modifier = Modifier.height(20.dp))
            for (accompany in accompanies) {
                var avatarResId: Int = when (val value = accompany[0]) {
                    is Int -> value
                    is String -> value.toIntOrNull()
                        ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                    else -> 0 // Nếu không phải Int hoặc String, trả về 0
                }
                var accompanyName: String = accompany[1].toString()
                RecentAccompany(avatarResId, accompanyName)
            }
            Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
            BottomNavigationBar(navController, userId, 4)
        }
    }
}