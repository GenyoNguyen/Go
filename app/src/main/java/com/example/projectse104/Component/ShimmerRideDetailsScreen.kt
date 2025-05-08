package com.example.projectse104.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.valentinilk.shimmer.shimmer
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.projectse104.core.Response
import com.example.projectse104.Component.*
@Composable
fun ShimmerRideDetailsScreen(navController: NavController,addGoButton: Boolean = true) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back + title shimmer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(200.dp)
                    .shimmer()
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Map shimmer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .shimmer()
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ride details placeholders
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer()
        ) {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(16.dp)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // GO button shimmer
        if (addGoButton) {
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .width(100.dp)
                    .height(30.dp)
                    .background(Color.LightGray, RoundedCornerShape(25.dp))
                    .shimmer()
            )
        }
    }
}