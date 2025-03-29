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
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import com.example.projectse104.*

@Composable
fun AddNewOfferScreen1(navController: NavController, userId: String) {
    var timeText by remember { mutableStateOf("Select Time") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BackArrowWithText(navController,"Add new offer")

        Spacer(modifier = Modifier.height(100.dp))
        // Icon đồng hồ

        AddNewOfferContent(R.drawable.clock_icon,"What time do you depart?")


        Spacer(modifier = Modifier.height(16.dp))
        TimePickerField(
            timeText = timeText,
            onValueChange = { selectedTime ->
                timeText = selectedTime
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(Modifier.fillMaxWidth(0.8f)) {
            BigButton(navController, "NEXT", {navController.navigate("add_new_offer2/$userId")})
        }
    }
}