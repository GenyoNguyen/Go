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
                    showToastMessage(context, "Đổi mật khẩu thành công")
                    isSubmitting = false
                    navController.popBackStack()
                }
                is ChangePasswordEvent.Error -> {
                    showToastMessage(context, "Lỗi: ${event.e?.message ?: "Lỗi không xác định"}")
                    isSubmitting = false
                }
                is ChangePasswordEvent.Loading -> {
                    showToastMessage(context, "Đang xử lý...")
                    isSubmitting = true
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
            text = "Đổi Mật Khẩu",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomPasswordTextField(
            label = "Mật khẩu hiện tại",
            value = state.currentPassword,
            onValueChange = { viewModel.onEvent(ChangePasswordFormEvent.CurrentPasswordChanged(it)) },
            error = state.currentPasswordError
        )

        CustomPasswordTextField(
            label = "Mật khẩu mới",
            value = state.newPassword,
            onValueChange = { viewModel.onEvent(ChangePasswordFormEvent.NewPasswordChanged(it)) },
            error = state.newPasswordError
        )

        CustomPasswordTextField(
            label = "Xác nhận mật khẩu mới",
            value = state.confirmPassword,
            onValueChange = { viewModel.onEvent(ChangePasswordFormEvent.ConfirmPasswordChanged(it)) },
            error = state.confirmPasswordError
        )

        Spacer(modifier = Modifier.height(24.dp))

        BigButton(
            navController = navController,
            text = "XÁC NHẬN",
            onClick = { viewModel.onEvent(ChangePasswordFormEvent.Submit) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Hủy",
            fontSize = 14.sp,
            color = Color(0xFF8FC79A),
            modifier = Modifier
                .clickable { navController.popBackStack() }
                .padding(16.dp)
        )
    }
}
