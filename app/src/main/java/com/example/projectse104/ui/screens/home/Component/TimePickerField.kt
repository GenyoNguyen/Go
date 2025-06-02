package com.example.projectse104.ui.screens.home.Component

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DateTimePickerField(
    dateTimeText: String,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFEFF8F2))
            .clickable { showDateTimePicker(context) { selectedDateTime -> onValueChange(selectedDateTime) } },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dateTimeText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (dateTimeText == "Select Date and Time") Color.Gray else Color.Black
        )
    }
}

// Hàm hiển thị `DatePickerDialog` và `TimePickerDialog` kết hợp
fun showDateTimePicker(context: Context, onDateTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    // Hiển thị DatePickerDialog trước
    DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
        // Sau khi chọn ngày, hiển thị TimePickerDialog
        TimePickerDialog(context, { _, selectedHour, selectedMinute ->
            // Tạo Calendar với các giá trị đã chọn
            val selectedCalendar = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)
            }
            // Định dạng kết quả thành chuỗi (e.g., "2025-05-17 12:27")
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            formatter.timeZone = java.util.TimeZone.getTimeZone("Asia/Bangkok") // Đảm bảo múi giờ +07:00
            val formattedDateTime = formatter.format(selectedCalendar.time)
            onDateTimeSelected(formattedDateTime)
        }, hour, minute, true).show()
    }, year, month, day).show()
}