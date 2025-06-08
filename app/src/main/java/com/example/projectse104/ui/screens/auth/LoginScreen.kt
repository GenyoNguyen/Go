package com.example.projectse104.ui.screens.auth

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
    var showVerificationDialog by remember { mutableStateOf(false) } // Thêm trạng thái cho hộp thoại

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    showToastMessage(context, "Đăng nhập thành công")
                    navController.navigate("login_successful/${event.userId}") {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
                is ValidationEvent.Error -> {
                    if (event.e?.message?.contains("Email chưa được xác minh") == true) {
                        showVerificationDialog = true // Hiển thị hộp thoại nếu email chưa xác minh
                    } else {
                        showToastMessage(context, "Lỗi: ${event.e?.message ?: "Lỗi không xác định"}")
                    }
                }
                is ValidationEvent.Loading -> {
                    showToastMessage(context, "Đang tải...")
                }
                is ValidationEvent.PendingVerification -> {
                    // Không dùng trong login, nhưng giữ cho tương thích
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
            text = "Đăng nhập",
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
            label = "Mật khẩu",
            value = state.password,
            onValueChange = { viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) },
            error = state.passwordError
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Quên mật khẩu?",
                color = Color(0xFF8FC79A),
                fontSize = 14.sp,
                modifier = Modifier.clickable { navController.navigate("forgot_password") }.padding(24.dp)
            )
        }

        BigButton(
            navController = navController,
            text = "ĐĂNG NHẬP",
            onClick = { viewModel.onEvent(LoginFormEvent.Submit) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(text = "Chưa có tài khoản? ", fontSize = 14.sp, color = Color.Gray)
            Text(
                text = "Đăng ký.",
                fontSize = 14.sp,
                color = Color(0xFF8FC79A),
                modifier = Modifier.clickable { navController.navigate(Screen.SignUp.route) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        SocialMedia()

        Spacer(modifier = Modifier.height(32.dp))
    }

    if (showVerificationDialog) {
        AlertDialog(
            onDismissRequest = { showVerificationDialog = false },
            title = { Text("Xác minh Email") },
            text = { Text("Email của bạn chưa được xác minh. Vui lòng kiểm tra email để xác minh tài khoản.") },
            confirmButton = {
                Button(onClick = {
                    showVerificationDialog = false
                    // Có thể điều hướng đến màn hình xác minh hoặc gửi lại email xác minh qua Firebase nếu cần
                }) {
                    Text("Đã hiểu")
                }
            }
        )
    }
}