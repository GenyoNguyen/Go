package com.example.projectse104.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.example.projectse104.ui.screens.auth.Component.*


@Composable
fun SignUpAndSignInScreen(navController: NavController) {
    // Lấy chiều cao của màn hình
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize() // Đảm bảo Box chiếm toàn bộ màn hình
    ) {
        // Hình ảnh đầu trang chiếm 60% chiều cao màn hình
        Box(
            modifier = Modifier
                .fillMaxWidth() // Chiếm 100% chiều rộng
                .height(screenHeight * 0.6f) // Chiếm 60% chiều cao màn hình
        ) {
            // Các hình ảnh sẽ được xếp chồng lên nhau
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Hình ảnh Susi3 nằm ở đáy
                Image(
                    painter = painterResource(id = R.drawable.susi3), // Thay bằng tên ảnh của bạn
                    contentDescription = "Sign Up Image",
                    modifier = Modifier
                        .fillMaxSize() // Đảm bảo ảnh chiếm toàn bộ không gian Box
                        .align(Alignment.BottomCenter) // Căn chỉnh ảnh ở dưới cùng
                )

                // Hình ảnh Susi2 ở trên Susi3
                Image(
                    painter = painterResource(id = R.drawable.susi1), // Thay bằng tên ảnh của bạn
                    contentDescription = "Sign Up Image 1",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(300.dp)// Căn giữa theo chiều ngang và dọc
                )

                // Hình ảnh Susi1 ở trên Susi2
                Image(
                    painter = painterResource(id = R.drawable.susi2), // Thay bằng tên ảnh của bạn
                    contentDescription = "Sign Up Image 2",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(125.dp)
                        .padding(top = 50.dp)// Căn chỉnh ảnh ở trên cùng
                )
            }
        }

        // Nội dung văn bản và các nút điều hướng
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Padding 16dp cho các văn bản
                .align(Alignment.BottomCenter), // Căn các phần tử xuống dưới
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom // Sử dụng Bottom để căn phần tử xuống dưới
        ) {
            // Tiêu đề cho màn hình
            Text(
                text = "Every journey is better when shared",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 10.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                // Thêm padding dưới
            )

            // Mô tả
            Text(
                text = "Join thousands of people who are using\nGo to find safe and convenient rides every day.",
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(start = 35.dp, end = 35.dp, bottom = 30.dp) // Thêm padding dưới và ngang
                // Thêm padding dưới
            )
            //Nút SIGN UP
            BigButton(navController,"SIGN UP",{navController.navigate("sign_up")})

            // Dòng chuyển qua Sign In (hiển thị trên 1 hàng văn bản liên tục)
            Spacer(modifier = Modifier.height(10.dp)) // Giảm khoảng cách nếu cần
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center // Đảm bảo các phần tử nằm trên cùng một dòng
            ) {
                // Nội dung "Already have an account? "
                Text(
                    text = "Already have an account? ",
                    fontSize = 14.sp, // Điều chỉnh font size cho phù hợp
                    color = Color.Gray
                )

                androidx.compose.material3.Text(
                    text = "Sign in.",
                    fontSize = 14.sp,
                    color = Color(0xFF8FC79A),
                    modifier = Modifier.clickable { navController.navigate("sign_in") }
                )
            }

        }
    }
}
