package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
@Composable
fun AddNewOfferScreen1(navController: NavController, userName: String) {
    var departureTime by remember { mutableStateOf("") } // Trạng thái nhập thời gian

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp), // Thêm padding để căn chỉnh
            verticalAlignment = Alignment.CenterVertically, // Căn giữa theo chiều dọc
            horizontalArrangement = Arrangement.Start // Căn trái để mũi tên ở góc trái
        ) {
            // Mũi tên quay lại
            IconButton(onClick = { navController.navigate("offer_a_ride/$userName") }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "Add new ofer", // Sử dụng tham số rideNo
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Căn giữa theo chiều ngang
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp) // Căn giữa hoàn toàn
            )
        }

        Spacer(modifier = Modifier.height(100.dp))

        // Icon đồng hồ
        Image(
            painter = painterResource(id = R.drawable.clock_icon), // Ảnh đồng hồ
            contentDescription = "Clock Icon",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Câu hỏi
        Text(
            text = "What time do you depart?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))
        TimePickerField()
        Spacer(modifier = Modifier.height(32.dp))

        // Nút NEXT
        Button(
            onClick = {
                navController.navigate("add_new_offer2/$userName")
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A))
        ) {
            Text(
                text = "NEXT",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun InputhBar() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) } // Quản lý trạng thái nhập văn bản

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
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
@Composable
fun TimePickerField() {
    val context = LocalContext.current
    var timeText by remember { mutableStateOf("Select Time") }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFEFF8F2))
            .clickable { showTimePicker(context) { selectedTime -> timeText = selectedTime } },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = timeText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (timeText == "Select Time") Color.Gray else Color.Black
        )
    }
}

// Hàm hiển thị `TimePickerDialog`
fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(context, { _, selectedHour, selectedMinute ->
        val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
        onTimeSelected(formattedTime)
    }, hour, minute, true).show()
}