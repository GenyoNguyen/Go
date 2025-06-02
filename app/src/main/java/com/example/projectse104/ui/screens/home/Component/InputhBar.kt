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
fun InputBar(
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String> // Thêm danh sách các tùy chọn
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(value) }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFEFF8F2))
            .clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (selectedOption.isEmpty()) "Select an option" else selectedOption,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (selectedOption.isEmpty()) Color.Gray else Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
                .shadow(4.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option, fontSize = 16.sp) },
                    onClick = {
                        selectedOption = option
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}