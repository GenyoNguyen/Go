package com.example.projectse104.ui.screens.home.Component

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import androidx.compose.material3.Card
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.valentinilk.shimmer.shimmer
import java.util.Calendar
import com.example.projectse104.ui.screens.home.Component.*

@Composable
fun ShimmerHomeScreen(navController: NavController, userId: String, index:Int,active:Int, userName: String) {
    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background), // Thay bằng ID của hình nền trong res/drawable
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            alpha = 0.2f


            // Hình nền sẽ được scale để phủ toàn màn hình
        )
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController, userId, active)
            }
            ,
            contentColor = Color.Red,
            containerColor = Color.Transparent, // Đặt containerColor trong suốt để thấy hình nền
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                HomeHeader(userName,"Home",R.drawable.header_home_real)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopNavBar(navController, userId, index,userName)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .shimmer(), // 💫 shimmer ở đây
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    repeat(6) { // giả lập 6 dòng shimmer
                        ShimmerRideItem()
                    }
                }
            }
        }
    }
}