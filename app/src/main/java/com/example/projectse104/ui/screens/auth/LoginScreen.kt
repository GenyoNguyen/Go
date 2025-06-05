package com.example.projectse104.ui.screens.auth

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
import com.example.projectse104.Component.CustomTextFieldWithLabel
import com.example.projectse104.core.showToastMessage
import com.example.projectse104.domain.use_case.data.ValidationEvent
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.ui.screens.auth.Component.SocialMedia

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    showToastMessage(context, "Login successful")
                    navController.navigate("login_successful/${event.userId}") {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
                is ValidationEvent.Error -> {
                    showToastMessage(context, "Error: ${event.e?.message ?: "Unknown error"}")
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
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Sign In",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.align(Alignment.Start)
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))


        CustomTextFieldWithLabel(
            label = "EMAIL",
            value = state.email,
            onValueChange = { viewModel.onEvent(LoginFormEvent.EmailChanged(it)) },
            error = state.emailError
        )


        CustomPasswordTextField(
            label = "Password",
            value = state.password,
            onValueChange = { viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) },
            error = state.passwordError
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Forgot password?",
                color = Color(0xFF8FC79A),
                fontSize = 14.sp,
                modifier = Modifier.clickable { navController.navigate("forgot_password") }.padding(24.dp)
            )
        }


        BigButton(
            navController = navController,
            text = "SIGN IN",
            onClick = { viewModel.onEvent(LoginFormEvent.Submit) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(text = "Don't have an account? ", fontSize = 14.sp, color = Color.Gray)
            Text(
                text = "Sign up.",
                fontSize = 14.sp,
                color = Color(0xFF8FC79A),
                modifier = Modifier.clickable { navController.navigate(Screen.SignUp.route) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        SocialMedia()

        Spacer(modifier = Modifier.height(32.dp))
    }
}