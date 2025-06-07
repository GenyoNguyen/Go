package com.example.projectse104.Component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.Composable

@Composable
fun BigButton(
    navController: NavController,
    text: String,
    onClick: () -> Unit = {},
){
    Box(modifier= Modifier.fillMaxWidth()){
        Button(
            onClick = { onClick()},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A))
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}