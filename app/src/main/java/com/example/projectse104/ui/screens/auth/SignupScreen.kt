package com.example.projectse104.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projectse104.Component.BigButton
import com.example.projectse104.Component.CustomPasswordTextField
import com.example.projectse104.Component.CustomTextFieldWithLabel
import com.example.projectse104.core.showToastMessage
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.ui.screens.auth.Component.SocialMedia
import com.example.projectse104.domain.use_case.data.ValidationEvent

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    var showVerificationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    showToastMessage(context, "Email verified successfully")
                    navController.navigate(Screen.SignIn.route)
                }
                is ValidationEvent.PendingVerification -> {
                    Log.d("SignupScreen", "Showing verification dialog")
                    showVerificationDialog = true
                }
                is ValidationEvent.Error -> {
                    showToastMessage(context, "Error: ${event.e?.message ?: "Unknown error"}")
                    showVerificationDialog = false // Đóng dialog nếu có lỗi
                }
                is ValidationEvent.Loading -> {
                    showToastMessage(context, "Loading...")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Sign up",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomTextFieldWithLabel(
            label = "NAME",
            value = state.fullName,
            onValueChange = { viewModel.onEvent(SignupFormEvent.FullNameChanged(it)) },
            error = state.fullNameError
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextFieldWithLabel(
            label = "EMAIL",
            value = state.email,
            onValueChange = { viewModel.onEvent(SignupFormEvent.EmailChanged(it)) },
            error = state.emailError
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomPasswordTextField(
            label = "Password",
            value = state.password,
            onValueChange = { viewModel.onEvent(SignupFormEvent.PasswordChanged(it)) },
            error = state.passwordError
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomPasswordTextField(
            label = "Confirm password",
            value = state.confirmPassword,
            onValueChange = { viewModel.onEvent(SignupFormEvent.ConfirmPasswordChanged(it)) },
            error = state.confirmPasswordError
        )

        Spacer(modifier = Modifier.height(36.dp))


        BigButton(
            navController = navController,
            text = "SIGN UP",
            onClick = { viewModel.onEvent(SignupFormEvent.Submit) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(text = "Already have an account? ", fontSize = 14.sp, color = Color.Gray)
            Text(
                text = "Sign in.",
                fontSize = 14.sp,
                color = Color(0xFF8FC79A),
                modifier = Modifier.clickable { navController.navigate(Screen.SignIn.route) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        SocialMedia()

        Spacer(modifier = Modifier.height(32.dp))

        if (showVerificationDialog) {
            AlertDialog(
                onDismissRequest = { showVerificationDialog = false },
                title = { Text("Verify Email") },
                text = { Text("An email has been sent to ${state.email}. Please verify your email to continue.") },
                confirmButton = {
                    Button(onClick = {
                        viewModel.onEvent(SignupFormEvent.VerifyEmail)
                        showVerificationDialog = false
                    }) {
                        Text("Check Verification")
                    }
                },
                dismissButton = {
                    Button(onClick = { showVerificationDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}