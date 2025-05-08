package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.*
import com.example.projectse104.Component.*

import com.example.projectse104.ui.screens.home.Component.*

@Composable
fun AddNewOfferScreen4(navController: NavController, userId: String) {
    LaunchedEffect(true) {
        // Delay for 2 seconds
        kotlinx.coroutines.delay(1000)
        // Navigate to the specified route after delay
        navController.navigate("add_new_offer_successfully/$userId")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.estimate_icon), // Ảnh đồng hồ
            contentDescription = "Clock Icon",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Câu hỏi
        Text(
            text = "Estimated Ké Coins to be earned",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}