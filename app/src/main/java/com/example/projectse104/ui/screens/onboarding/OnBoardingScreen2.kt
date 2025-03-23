package com.example.projectse104.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen

@Composable
fun OnBoardingScreen2(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { navController.navigate(Screen.OnBoarding3.route) } // 👉 Chuyển sang OnBoarding 3 khi bấm vào
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding_topright_shape), // Đổi tên ảnh nếu khác
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.TopEnd)
                .offset(x = -60.dp, y = 90.dp)
                .zIndex(1f), // Điều chỉnh để không che khuất ảnh chính
            contentScale = ContentScale.Fit,

            )

        // Vùng màu xanh lá phía dưới bên trái
        Image(
            painter = painterResource(id = R.drawable.onboarding_bottomleft_shape), // Đổi tên ảnh nếu khác
            contentDescription = null,
            modifier = Modifier
                .size(110.dp)
                .align(Alignment.BottomStart)
                .offset(x = (40).dp, y = -340.dp)
                .zIndex(1f), // Điều chỉnh để không bị che khuất
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header với nút "Skip"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Skip",
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { navController.navigate(Screen.SignUpAndSignIn.route) } // Chuyển sang màn hình đăng nhập
                )
            }

            // Ảnh minh họa chính
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp) // Đẩy xuống để tránh che vùng xanh trên
                    .height(250.dp), // Điều chỉnh kích thước
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.onboarding2), // Sử dụng ảnh từ drawable
                    contentDescription = "Onboarding Image",
                    modifier = Modifier
                        .size(220.dp) // Điều chỉnh để ảnh nhỏ hơn, không che mất vùng xanh
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Tiêu đề
            Text(
                text = "Connect with a Driver",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8FC79A) // Màu xanh nhạt
            )

            // Mô tả
            Text(
                text = "Once you've found your destination, connect with a driver to arrange your ride. Chat, confirm details, and ready to go",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Chỉ báo vị trí trang (indicator dots)
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == 1) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (index == 1) Color.Gray else Color.LightGray)
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
