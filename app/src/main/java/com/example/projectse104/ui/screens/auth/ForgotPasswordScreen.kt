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
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.example.projectse104.Component.*
import com.example.projectse104.ui.screens.auth.Component.*

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
        BackArrowWithText(navController,"Forgot password")

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
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)){
        BigButton(navController,"SEND",{navController.navigate("verify_email")})

        Spacer(modifier = Modifier.height(16.dp)) // Khoảng cách dưới nút SEND nếu cần
    }
}}
