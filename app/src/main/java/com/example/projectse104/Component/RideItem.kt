package com.example.projectse104.Component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projectse104.R
import com.example.projectse104.core.toCustomString
import com.example.projectse104.ui.screens.home.Component.AlertDialog
import java.util.Date

@Composable
fun RideItem(
    navController: NavController,
    rideNo: String = "",
    rideId: String = "",
    estimatedDeparture: String = "",
    fromLocation: String = "",
    toLocation: String = "",
    avatarResId: String?,
    route: String = "",
    userId: String = "",
    riderId: String?,
    addGoButton: String = "",
    canEnd: Boolean = false,
    canStart: Boolean = false,
    startRide: (rideId: String) -> Unit = {},
    endRide: (rideId: String) -> Unit = {}
) {
    val path: String = when (route) {
        "ride_details" -> "$route/$userId/$rideId/$addGoButton"
        "ride_details_history" -> "$route/$userId/$rideId"
        else -> "$route/$userId/$rideId"
    }

    val openStartRideDialog = remember { mutableStateOf(false) }
    val openEndRideDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFF8FC79A))
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f) // Đảm bảo Column chiếm không gian hợp lý
            ) {
                Text(
                    text = "Ride No. $rideNo",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Estimated Departure",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = estimatedDeparture,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vector),
                        contentDescription = "Path Icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "From: $fromLocation - To: $toLocation",
                        color = Color(0xFF8FC79A),
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f) // Ngăn text bị tràn
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            AsyncImage(
                model = avatarResId,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color(0xFFE0E0E0), CircleShape) // Thêm viền nhẹ cho avatar
                    .clickable { /* Có thể thêm logic để mở image picker nếu cần */ },
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (canEnd) {
                Button(
                    onClick = { openEndRideDialog.value = true },
                    modifier = Modifier
                        .width(80.dp)
                        .height(28.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = "End Ride",
                        fontSize = 13.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (canStart) {
                if (estimatedDeparture <= Date().toCustomString()) {
                    Button(
                        onClick = { openStartRideDialog.value = true },
                        modifier = Modifier
                            .width(80.dp)
                            .height(28.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "Start Ride",
                            fontSize = 13.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Button(
                onClick = { navController.navigate(path) },
                modifier = Modifier
                    .width(80.dp)
                    .height(28.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                Text(
                    text = "Details",
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            riderId?.let {
                Button(
                    onClick = { navController.navigate("chat_details/$userId/$riderId") },
                    modifier = Modifier
                        .width(80.dp)
                        .height(28.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFF35B82A)),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Contact",
                        fontSize = 13.sp,
                        color = Color(0xFF35B82A),
                        fontWeight = FontWeight.Bold
                    )
                }
            } ?: run {
                ToastMessage(message = "Không thể liên hệ tài xế!", show = true)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when {
            openStartRideDialog.value -> {
                AlertDialog(
                    dialogTitle = "Confirm Action",
                    dialogText = "Are you sure you want to proceed?",
                    icon = Icons.Default.Info,
                    onDismissRequest = {
                        openStartRideDialog.value = false
                    },
                    onConfirmation = {
                        openStartRideDialog.value = false
                        startRide(rideId)
                    }
                )
            }

            openEndRideDialog.value -> {
                AlertDialog(
                    dialogTitle = "Confirm Action",
                    dialogText = "Are you sure you want to end the ride?",
                    icon = Icons.Default.Info,
                    onDismissRequest = {
                        openEndRideDialog.value = false
                    },
                    onConfirmation = {
                        openEndRideDialog.value = false
                        endRide(rideId)
                    }
                )
            }
        }

    }
}