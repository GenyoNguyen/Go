package com.example.projectse104.ui.screens.profile.Component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.projectse104.R
import com.example.projectse104.Component.*

@Composable
fun RecentAccompany(
    avatarURL: String,
    passengerName: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Ensure column takes full width
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Align and add spacing between rows
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                modifier = Modifier
                    .weight(1f), // Take half the width
                horizontalAlignment = Alignment.CenterHorizontally // Center content
            ) {
                // Avatar
                AsyncImage(
                    model = avatarURL,
                    contentDescription = "Passenger Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape) // Use CircleShape to match ViewUserDetails
                        .border(2.dp, Color(0xFFE0E0E0), CircleShape), // Add light border
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.avatar_1), // Add a placeholder
                    error = painterResource(id = R.drawable.avatar_1) // Fallback for failed loads
                )
            }
            Spacer(modifier = Modifier.width(16.dp)) // Reduced width for better balance
            Column(
                modifier = Modifier
                    .weight(1f), // Take half the width
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Go with",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = passengerName,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        // Dashed line at the bottom
        Divider(
            color = Color(0xFF8FC79A),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth() // Ensure the divider spans the container
                .padding(top = 8.dp) // Space between content and divider
        )
    }
}