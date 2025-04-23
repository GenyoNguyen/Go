package com.example.projectse104.ui.screens.home.Component


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

@Composable
fun TimePickerField(
    timeText: String,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFEFF8F2))
            .clickable { showTimePicker(context) { selectedTime -> onValueChange(selectedTime) } },
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
