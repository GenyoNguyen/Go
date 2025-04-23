package com.example.projectse104.ui.screens.home.Component

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.material3.Card
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.valentinilk.shimmer.shimmer
import java.util.Calendar
import com.example.projectse104.ui.screens.home.Component.*

@Composable
fun InputhBar(value:String,onValueChange:(String)->Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = 16.dp)
            .height(50.dp) // Tăng nhẹ chiều cao để có thêm không gian hiển thị
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFEFF8F2)),
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp // Đảm bảo dòng chữ không bị cắt
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFEFF8F2),
            unfocusedContainerColor = Color(0xFFEFF8F2),
            disabledContainerColor = Color(0xFFEFF8F2),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}