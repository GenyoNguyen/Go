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


@Composable
fun AddNewOfferScreen2(navController: NavController, userId: String) {
    var departureTime by remember { mutableStateOf("") } // Trạng thái nhập thời gian

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BackArrowWithText(navController,"Add new offer")

        Spacer(modifier = Modifier.height(100.dp))

        // Icon đồng hồ
        Image(
            painter = painterResource(id = R.drawable.flag_icon), // Ảnh đồng hồ
            contentDescription = "Clock Icon",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Câu hỏi
        Text(
            text = "Where is the departure location?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))
        InputhBar()
        Spacer(modifier = Modifier.height(32.dp))

        Column(Modifier.fillMaxWidth(0.8f)) {
            BigButton(navController, "NEXT", {navController.navigate("add_new_offer3/$userId")})
        }
    }
}