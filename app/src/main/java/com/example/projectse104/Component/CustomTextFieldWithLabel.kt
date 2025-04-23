package com.example.projectse104.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun CustomTextFieldWithLabel(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusedColor = Color(0xFF8FC79A)

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (value.isNotEmpty()) focusedColor else Color.Gray,
            modifier = Modifier
                .padding(start = 16.dp)
                .offset(y = -7.dp)
                .zIndex(1f)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = focusedColor,
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = focusedColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        val coverWidth = when (label.length) {
            in 1..5 -> 55.dp
            in 6..8 -> 75.dp
            else -> 90.dp
        }

        Box(
            modifier = Modifier
                .width(coverWidth)
                .height(6.dp)
                .background(Color.White)
                .offset(x = 20.dp, y = 7.dp)
        )
    }
}