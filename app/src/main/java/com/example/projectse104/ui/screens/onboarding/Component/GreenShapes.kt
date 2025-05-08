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