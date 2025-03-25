package com.example.projectse104.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.*

@Composable
fun NewPasswordScreen(navController: NavController) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackArrowWithText(navController,"Enter new password and confirm")


        Spacer(modifier = Modifier.height(24.dp))

        // New password field
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal=24.dp)) {
            CustomPasswordTextField("New password",
                newPassword,
                {newPassword=it})
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm password field
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal=24.dp)) {
            CustomPasswordTextField(
                label = "Confirm new password",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal=24.dp)){
            BigButton(navController,"CHANGE PASSWORD",{navController.navigate("reset_password_successful"  )})
        }
    }
}