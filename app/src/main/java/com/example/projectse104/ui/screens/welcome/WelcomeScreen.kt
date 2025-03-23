package com.example.projectse104.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavController) {
    // Sử dụng LaunchedEffect để xử lý tự động chuyển màn hình
    LaunchedEffect(true) {
        delay(2000)  // Chờ 2 giây
        navController.navigate(Screen.OnBoarding1.route)  // Chuyển sang OnBoarding1
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7CCFA7)) // Màu nền tương ứng với XML
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hình ảnh chào mừng
        Image(
            painter = painterResource(id = R.drawable.logo), // Cần thêm ảnh vào res/drawable
            contentDescription = "Welcome Image",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Go everywhere with smile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
