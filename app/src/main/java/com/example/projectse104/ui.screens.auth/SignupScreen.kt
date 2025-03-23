package com.example.projectse104.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen

@Composable
fun SignupScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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

        CustomTextFieldWithIcon(
            label = "NAME",
            value = name,
            onValueChange = { name = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextFieldWithIcon(
            label = "EMAIL",
            value = email,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomPasswordField(
            label = "Password",
            value = password,
            onValueChange = { password = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomPasswordField(
            label = "Confirm password",
            value = confirmPassword,
            onValueChange = { confirmPassword = it }
        )

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = { /* Xử lý đăng ký */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A))
        ) {
            Text(
                text = "SIGN UP",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.facebook), contentDescription = "Facebook", modifier = Modifier.size(40.dp).clickable {})
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.twitter), contentDescription = "Twitter", modifier = Modifier.size(40.dp).clickable {})
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.google), contentDescription = "Google", modifier = Modifier.size(40.dp).clickable {})
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun CustomPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusedColor = Color(0xFF8FC79A)
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        // Nhãn được đẩy xuống để che mất một phần TextField
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (value.isNotEmpty()) focusedColor else Color.Gray,
            modifier = Modifier
                .padding(start = 16.dp)
                .offset(y = -7.dp) // Đảm bảo label không bị che khuất
                .zIndex(1f)
        )

        // Trường nhập mật khẩu có icon mắt
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.Black), // Màu chữ trong TextField
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp), // Bo góc cho TextField
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = focusedColor
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = focusedColor,
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = focusedColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        // Lớp phủ để che viền trên đúng phần chữ
        val coverWidth = when (label.length) {
            in 1..5 -> 55.dp
            in 6..8 -> 75.dp
            else -> 120.dp
        }

        Box(
            modifier = Modifier
                .width(coverWidth) // Điều chỉnh độ dài lớp che dựa trên nhãn
                .height(6.dp) // Độ cao nhỏ để không che quá nhiều
                .background(Color.White) // Cùng màu nền
                .offset(x = 20.dp, y = 7.dp) // Điều chỉnh vị trí đúng vị trí chữ
        )
    }
}

@Composable
fun CustomTextFieldWithIcon(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusedColor = Color(0xFF8FC79A)
    val isValid = value.isNotEmpty()

    Box(modifier = Modifier.fillMaxWidth()) {
        // Nhãn được đẩy xuống để che mất một phần TextField
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (value.isNotEmpty()) focusedColor else Color.Gray,
            modifier = Modifier
                .padding(start = 16.dp)
                .offset(y = -7.dp) // Hạ chữ xuống để che viền
                .zIndex(1f)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            trailingIcon = {
                if (isValid) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = "Valid", tint = focusedColor)
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = focusedColor,
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = focusedColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        // Lớp phủ để che viền trên đúng phần chữ
        val coverWidth = when (label.length) {
            in 1..5 -> 55.dp
            in 6..8 -> 75.dp
            else -> 90.dp
        }

        Box(
            modifier = Modifier
                .width(coverWidth)
                .height(6.dp)
                .background(Color.White)
                .offset(x = 20.dp, y = 7.dp)
        )
    }
}
