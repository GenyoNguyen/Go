package com.example.projectse104.Component

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projectse104.R

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
    addGoButton: String = ""
) {
    val path: String = when (route) {
        "ride_details" -> "$route/$userId/$rideId/$addGoButton"
        "ride_details_history" -> "$route/$userId/$rideId"
        else -> "$route/$userId/$rideId"
    }

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

        Button(
            onClick = { navController.navigate(path) },
            modifier = Modifier
                .width(80.dp)
                .height(28.dp)
                .align(Alignment.End),
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

        Spacer(modifier = Modifier.height(12.dp))
    }
}