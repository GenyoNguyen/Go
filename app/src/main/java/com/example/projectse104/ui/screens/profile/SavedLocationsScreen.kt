package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.projectse104.*
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User

@Composable
fun SavedLocationScreen(navController: NavController, userId: String) {
    var savedLocatons:List<List<Any>> = listOf(
        listOf(R.drawable.saved_location_home,"Home","KTX khu B, phường Linh Trung, thành phố Thủ Đức "),
        listOf(R.drawable.saved_location_work,"Work","Đại học Công nghệ Thông tin ĐHQG TP Hồ Chí Minh"),
        listOf(R.drawable.saved_location_other,"Other","Tòa BA4, KTX khu B"),
        )
    var isLoading:Boolean=true
    var loadingFailed:Boolean=false
    val state: Response<User> = Response.Success(
        User(id=userId, fullName = "Nguyễn Xuân Phúc",
        email="nguyenxuanphuc010205@gmail.com", profilePic = R.drawable.avatar.toString())
    )
    when(state){
        is Response.Success<User> -> {
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
        ShimmerSavedLocationScreen(navController)
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            BackArrowWithText(navController, "Saved location")

            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                for (savedLocation in savedLocatons) {
                    var iconID: Int = when (val value = savedLocation[0]) {
                        is Int -> value
                        is String -> value.toIntOrNull()
                            ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                        else -> 0 // Nếu không phải Int hoặc String, trả về 0
                    }
                    var name = savedLocation[1].toString()
                    var details = savedLocation[2].toString()
                    SavedLocation(iconID, name, details)
                }
            }
            Spacer(modifier = Modifier.height(50.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                BigButton(navController = navController,
                    text = "ADD NEW ADDRESS",
                    onClick = {})
            }

            Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
            BottomNavigationBar(navController, userId, 4)
        }
    }
}