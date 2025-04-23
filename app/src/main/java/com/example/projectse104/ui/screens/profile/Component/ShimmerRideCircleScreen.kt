package com.example.projectse104.ui.screens.profile.Component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.rememberShimmer
@Composable
fun ShimmerRideCircleScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header shimmer
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
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
                    .width(180.dp)
                    .height(24.dp)
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                    .shimmer()

            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // RideCircleDetails shimmer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray, CircleShape)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(16.dp)
                        .background(Color.LightGray, RoundedCornerShape(6.dp))
                )
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(16.dp)
                        .background(Color.LightGray, RoundedCornerShape(6.dp))
                )
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(16.dp)
                        .background(Color.LightGray, RoundedCornerShape(6.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Title "Your favourite rider"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(24.dp)
                    .background(Color.LightGray, RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable shimmer danh sách riders
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .shimmer()
        ) {
            repeat(6) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.LightGray, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(18.dp)
                            .background(Color.LightGray, RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.LightGray, CircleShape)
                    )
                }
                Divider(color = Color(0XFF35B82A), thickness = 1.dp)
            }
        }
    }
}
