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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projectse104.Component.BackArrowWithText
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.profile.Component.RecentAccompany
import com.example.projectse104.ui.screens.profile.Component.ShimmerProfileViewScreen
import com.example.projectse104.ui.screens.profile.Component.ViewRideDetails
import com.example.projectse104.ui.screens.profile.Component.ViewUserDetails

@Composable
fun ProfileViewScreen(
    navController: NavController,
    userId: String,
    hideNav: String = "yes",
    viewModel: ProfileViewModel = hiltViewModel()
) {
    // Thu thập trạng thái từ ViewModel
    val userState = viewModel.userState.collectAsState().value
    val avatarUrl = viewModel.avatarUrl.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value

    // Khởi tạo các biến trạng thái
    var userFullName: String = ""
    var rating: String = ""
    var position: String = ""
    var ridesTaken: String = "0"
    var ridesGiven: String = "0"
    var trustScore: String = "0"
    var profilePicUrl: String? = null
    var loadingFailed: Boolean = false

    // Danh sách accompanies (có thể lấy từ API hoặc ViewModel sau này)
    val accompanies: List<List<Any>> = listOf(
        listOf(R.drawable.avatar_1, "Nguyễn Hữu Dũng"),
        listOf(R.drawable.avatar_2, "Nguyễn Phong Huy")
    )

    // Xử lý trạng thái người dùng
    when (userState) {
        is Response.Success<User> -> {
            userFullName = userState.data?.fullName.orEmpty()
            rating = userState.data?.overallRating?.toString().orEmpty()
            position = userState.data?.location.orEmpty()
            // Giả sử ridesTaken, ridesGiven, trustScore được cung cấp từ User hoặc API
            ridesTaken = "27" // Thay thế bằng dữ liệu thực từ User nếu có
            ridesGiven = "36"
            trustScore = "209"
            loadingFailed = false
        }
        is Response.Loading -> {
            // Đã có isLoading từ ViewModel
        }
        is Response.Failure -> {
            loadingFailed = true
        }
        else -> {
            loadingFailed = true
        }
    }

    // Xử lý URL ảnh đại diện
    when (avatarUrl) {
        is Response.Success<String> -> {
            profilePicUrl = avatarUrl.data
        }
        is Response.Failure -> {
            profilePicUrl = null // Sẽ hiển thị ảnh mặc định trong ViewUserDetails
        }
        else -> {
            profilePicUrl = null
        }
    }

    // Hiển thị Toast nếu tải thất bại
    ToastMessage(
        message = "Không thể tải dữ liệu. Vui lòng thử lại!",
        show = loadingFailed
    )

    // Hiển thị giao diện
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
                profilePicUrl = profilePicUrl,
                userFullName = userFullName,
                rating = rating,
                position = position
            )
            Spacer(modifier = Modifier.height(20.dp))

            ViewRideDetails(
                ridesTaken = ridesTaken,
                ridesGiven = ridesGiven,
                trustScore = trustScore
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
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            for (accompany in accompanies) {
                val avatarResId: Int = when (val value = accompany[0]) {
                    is Int -> value
                    is String -> value.toIntOrNull() ?: 0
                    else -> 0
                }
                val accompanyName: String = accompany[1].toString()
                RecentAccompany(avatarResId, accompanyName)
            }
            Spacer(modifier = Modifier.weight(1f))
            if (hideNav == "yes") {
                BottomNavigationBar(navController, userId, 4)
            }
        }
    }
}