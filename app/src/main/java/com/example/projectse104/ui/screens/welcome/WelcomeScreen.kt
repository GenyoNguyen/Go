package com.example.projectse104.ui.screens.welcome

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.utils.DataStoreSessionManager
import com.example.projectse104.utils.UserSessionInfo
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavController) {
    val context = LocalContext.current
    val sessionManager = DataStoreSessionManager(context)
    val userInfo by sessionManager.userInfo.collectAsState(
        initial = UserSessionInfo(false, null, null, null)
    )

    LaunchedEffect(userInfo) {
        Log.d("WelcomeScreen", "userInfo: $userInfo")
        delay(5000)
        if (userInfo.isLoggedIn && userInfo.userId != null) {
            Log.d("WelcomeScreen", "Navigating to home/${userInfo.userId}")
            navController.navigate("home/${userInfo.userId}") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        } else {
            Log.d("WelcomeScreen", "Navigating to onboarding1")
            navController.navigate(Screen.OnBoarding1.route) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7CCFA7))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Welcome Image",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Go everywhere with smile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(20.dp))
        CircularProgressIndicator(color = Color.White) // Hiển thị loading trong lúc chờ
    }
}