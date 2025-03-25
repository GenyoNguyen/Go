package com.example.projectse104.ui.screens.auth

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.ui.navigation.Screen
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.example.projectse104.*

@Composable
fun VerifyEmailScreen(navController: NavController) {
    var otpCode by remember { mutableStateOf(List(5) { "" }) } // List of 5 empty strings for OTP
    val focusRequesters = List(5) { FocusRequester() } // Create a list of FocusRequesters

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackArrowWithText(navController,"Verify your email")
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Enter your OTP code here",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp).padding(horizontal = 24.dp)
        )

        // OTP Input Fields
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        ) {
            // Loop through the OTP code list and create text fields for each
            otpCode.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value,
                    onValueChange = {
                        if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                            otpCode = otpCode.toMutableList().apply { this[index] = it }
                            // Move to next field if a digit is entered
                            if (index < otpCode.size - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .width(40.dp)
                        .height(50.dp)
                        .focusRequester(focusRequesters[index]), // Attach the FocusRequester
                    maxLines = 1,
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFF8FC79A),
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = Color(0xFF8FC79A),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)){
            BigButton(navController,"VERIFY",{navController.navigate("new_password")})
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Resend OTP
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Didn't receive the OTP? ",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Text(
                text = "Resend.",
                fontSize = 14.sp,
                color = Color(0xFF8FC79A),
                modifier = Modifier.clickable { /* Handle resend OTP */ }
            )
        }
    }
}

