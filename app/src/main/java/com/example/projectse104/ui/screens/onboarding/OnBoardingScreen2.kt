package com.example.projectse104.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.example.projectse104.ui.screens.onboarding.Component.*

@Composable
fun OnBoardingScreen2(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount < 0) {
                        navController.navigate(Screen.OnBoarding3.route) // Thay "onboarding2_route" với route của bạn
                    } else if (dragAmount > 0) {
                        navController.navigate(Screen.OnBoarding1.route) // Thay "onboarding2_route" với route của bạn
                    }
                }
            }
    ) {
        Content(navController = navController,
            drawableID = R.drawable.onboarding2,
            heading = "Connect with a driver",
            description = "Once you've found your destination, connect with a driver to arrange your ride. Chat, confirm details, and ready to go",
            pageIndex=1)

        }
    }
