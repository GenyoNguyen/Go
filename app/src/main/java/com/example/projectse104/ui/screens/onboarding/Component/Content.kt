package com.example.projectse104.ui.screens.onboarding.Component

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