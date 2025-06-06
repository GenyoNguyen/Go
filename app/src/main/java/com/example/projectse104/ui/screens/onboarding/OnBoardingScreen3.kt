package com.example.projectse104.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import androidx.compose.ui.input.pointer.pointerInput
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.example.projectse104.ui.screens.onboarding.Component.*


@Composable
fun OnBoardingScreen3(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount > 0) {
                        navController.navigate(Screen.OnBoarding2.route) // Thay "onboarding2_route" với route của bạn
                    }
                }
            }
    ) {
        Content(
            navController = navController,
            drawableID = R.drawable.onboarding3,
            heading = "Enjoy Your Ride",
            description = "Hop in and enjoy a smooth, hassle-free ride to your destination. Sit back, relax and arrive safely!",
            pageIndex = 2
        )
        // Nút "Get Started"
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Thêm padding nếu cần
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BigButton(
                navController,
                "GET STARTED",
                {navController.navigate("sign_up_and_sign_in")}
            )
        }

    }
}