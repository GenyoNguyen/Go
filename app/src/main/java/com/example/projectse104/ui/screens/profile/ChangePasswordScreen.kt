package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projectse104.Component.BigButton
import com.example.projectse104.Component.CustomPasswordTextField
import com.example.projectse104.core.showToastMessage
import com.example.projectse104.ui.screens.profile.Component.ProfileCustomTextFieldWithLabel

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    userId: String,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    var isSubmitting by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = context) {
        viewModel.changePasswordEvents.collect { event ->
            when (event) {
                is ChangePasswordEvent.Success -> {
                    showToastMessage(context, "Password changed successfully")
                    isSubmitting = false
                    navController.popBackStack()
                }
                is ChangePasswordEvent.Error -> {
                    showToastMessage(context, "Error: ${event.e?.message ?: "Unknown error"}")
                    isSubmitting = false
                }
                is ChangePasswordEvent.Loading -> {
                    showToastMessage(context, "Processing...")
                    isSubmitting = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Change Password",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 24.dp)
        )

        ProfileCustomTextFieldWithLabel(
            label = "Current Password",
            value = state.currentPassword
        ) {
            viewModel.onEvent(ChangePasswordFormEvent.CurrentPasswordChanged(it))
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileCustomTextFieldWithLabel(
            label = "New Password",
            value = state.newPassword
        ) {
            viewModel.onEvent(ChangePasswordFormEvent.NewPasswordChanged(it))
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileCustomTextFieldWithLabel(
            label = "Confirm!",
            value = state.confirmPassword
        ) {
            viewModel.onEvent(ChangePasswordFormEvent.ConfirmPasswordChanged(it))
        }

        Spacer(modifier = Modifier.height(24.dp))

        BigButton(
            navController = navController,
            text = "SUBMIT",
            onClick = { viewModel.onEvent(ChangePasswordFormEvent.Submit) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Cancel",
            fontSize = 14.sp,
            color = Color(0xFF8FC79A),
            modifier = Modifier
                .clickable { navController.popBackStack() }
                .padding(16.dp)
        )
    }
}