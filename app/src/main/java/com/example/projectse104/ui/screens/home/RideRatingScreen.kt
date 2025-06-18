package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projectse104.Component.BackArrowWithText

@Composable
fun RideRatingScreen(
    navController: NavController,
    userId:String,
    rideId: String,
    viewModel: RideRatingViewModel = hiltViewModel()
) {
    var rating by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top, // Place content from the top
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackArrowWithText(navController, "Go back to ride details")
        Spacer(modifier = Modifier.height(24.dp))
        Text("Rate your ride", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = rating,
            onValueChange = { rating = it },
            label = { Text("Rating (0-5)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF8FC79A),
                focusedLabelColor = Color(0xFF8FC79A),
                cursorColor = Color(0xFF8FC79A)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Comment") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF8FC79A),
                focusedLabelColor = Color(0xFF8FC79A),
                cursorColor = Color(0xFF8FC79A)
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                val ratingValue = rating.toFloatOrNull()
                if (ratingValue == null || ratingValue < 0f || ratingValue > 5f) {
                    showError = true
                } else {
                    showError = false
                    isSubmitting = true
                    viewModel.submitRating(rideId, ratingValue, comment) {
                        isSubmitting = false
                        navController.navigate("history/$userId") {
                            popUpTo("history/$userId") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSubmitting,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A))
        ) {
            Text("Submit")
        }
        if (showError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Please enter a valid rating between 0 and 5.", color = MaterialTheme.colorScheme.error)
        }
        if (isSubmitting) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}