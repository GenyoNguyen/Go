package com.example.projectse104.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.ui.navigation.Screen

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 0.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Row chứa mũi tên và tiêu đề "Forgot password"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp), // Thêm padding để căn chỉnh
            verticalAlignment = Alignment.CenterVertically, // Căn giữa theo chiều dọc
            horizontalArrangement = Arrangement.Start // Căn trái để mũi tên ở góc trái
        ) {
            // Mũi tên quay lại
            IconButton(onClick = { navController.navigate(Screen.SignIn.route) }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tiêu đề "Forgot password"
            Text(
                text = "Forgot password",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Căn giữa theo chiều ngang
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp) // Căn giữa hoàn toàn
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Trường nhập EMAIL
        Column(
            modifier = Modifier
                .fillMaxWidth()  // Đảm bảo chiếm toàn bộ chiều rộng màn hình
                .padding(horizontal = 24.dp)
        ) {
            CustomTextFieldWithLabel(
                label = "EMAIL",
                value = email,
                onValueChange = { email = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp)) // Tạo khoảng cách giữa EMAIL và SEND

        // Nút "SEND"
        Button(
            onClick = {
                // Điều hướng đến VerifyEmailScreen khi nhấn nút SEND
                navController.navigate(Screen.VerifyEmail.route) // Chuyển đến VerifyEmailScreen
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A))
        ) {
            Text(
                text = "SEND",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Khoảng cách dưới nút SEND nếu cần
    }
}
