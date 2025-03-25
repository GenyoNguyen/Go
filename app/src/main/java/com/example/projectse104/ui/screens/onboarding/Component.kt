package com.example.projectse104.ui.screens.onboarding // Đảm bảo cùng package với OnBoardingScreen1

import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
@Composable
fun GreenShapes() {
    Box(
        modifier = Modifier.fillMaxSize() // Đảm bảo sử dụng Box để áp dụng align
    ) {
        // Vùng màu xanh lá phía trên bên phải
        Image(
            painter = painterResource(id = com.example.projectse104.R.drawable.onboarding_topright_shape), // Đổi tên ảnh nếu khác
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.TopEnd) // Đảm bảo sử dụng align trong Box
                .offset(x = -60.dp, y = 90.dp)
                .zIndex(1f), // Điều chỉnh để không che khuất ảnh chính
            contentScale = ContentScale.Fit
        )

        // Vùng màu xanh lá phía dưới bên trái
        Image(
            painter = painterResource(id = com.example.projectse104.R.drawable.onboarding_bottomleft_shape), // Đổi tên ảnh nếu khác
            contentDescription = null,
            modifier = Modifier
                .size(110.dp)
                .align(Alignment.BottomStart) // Đảm bảo sử dụng align trong Box
                .offset(x = (40).dp, y = -340.dp)
                .zIndex(1f), // Điều chỉnh để không bị che khuất
            contentScale = ContentScale.Fit
        )
    }
}
@Composable
fun SkipButton(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "Skip",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold, // Thêm font weight cho chữ
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    navController.navigate(Screen.SignUpAndSignIn.route) // Chuyển sang màn hình đăng nhập
                }
        )
    }
}
@Composable
fun IndicatiorDots(pageIndex:Int) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(3) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == pageIndex) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(if (index == pageIndex) Color.Gray else Color.LightGray)
                    .padding(4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
@Composable
fun Content(navController: NavController,drawableID:Int,heading:String,description:String,pageIndex:Int){
    Box(modifier = Modifier.fillMaxSize()){
        GreenShapes()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SkipButton(navController)

            // Ảnh minh họa chính
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp) // Đẩy xuống để tránh che vùng xanh trên
                    .height(250.dp), // Điều chỉnh kích thước
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = drawableID), // Sử dụng ảnh từ drawable
                    contentDescription = "Onboarding Image",
                    modifier = Modifier
                        .size(220.dp) // Điều chỉnh để ảnh nhỏ hơn, không che mất vùng xanh
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Tiêu đề
            Text(
                text = heading,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8FC79A) // Màu xanh nhạt
            )

            // Mô tả
            Text(
                text = description,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Chỉ báo vị trí trang (indicator dots)
            IndicatiorDots(pageIndex = pageIndex)

            Spacer(modifier = Modifier.height(16.dp))
    }
}}