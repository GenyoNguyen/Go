package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.Component.*
import com.example.projectse104.ui.screens.home.Component.*
@Composable
fun AddNewOfferScreen1(navController: NavController, userId: String) {
    var showError by remember { mutableStateOf(false) }
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
        DateTimePickerField(
            dateTimeText = timeText,
            onValueChange = { selectedTime ->
                timeText = selectedTime
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(Modifier.fillMaxWidth(0.8f)) {
            BigButton(navController, "NEXT") {
                if (timeText != "Select Time") {
                    navController.navigate("add_new_offer2/$userId/$timeText")
                } else {
                    showError = true
                }
            }

            if (showError) {
                Text(
                    text = "Please select a departure time before continuing.",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 12.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }}
    }
}