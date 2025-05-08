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
fun rideDetails(estimatedDeparture: String,
                fromLocation: String,
                toLocation: String,
                riderName:String,
                riderUserId:String,
                passengerName:String,
                passengerUserId:String,
                cost:String){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Time: $estimatedDeparture", // Sử dụng tham số estimatedDeparture
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Route: From $fromLocation to $toLocation", // Sử dụng tham số fromLocation và toLocation
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = buildAnnotatedString {
                    append("Rider: ")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold)) // Áp dụng đậm cho riderName
                    append(riderName) // In đậm tên của người lái
                    pop() // Kết thúc phần in đậm
                    append(" - UserID: ")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold)) // Áp dụng đậm cho riderUserId
                    append(riderUserId) // In đậm ID người lái
                    pop() // Kết thúc phần in đậm
                },
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Status: ",
                fontSize = 15.sp
            )
            Text(
                text = "Not Started", // Trạng thái cố định, bạn có thể thay đổi nếu cần
                fontSize = 15.sp,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.Start) {
            Text(
                text = buildAnnotatedString {
                    append("Passenger Information: ")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold)) // Áp dụng đậm cho riderName
                    append(passengerName) // In đậm tên của người lái
                    pop() // Kết thúc phần in đậm
                    append(" - UserID: ")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold)) // Áp dụng đậm cho riderUserId
                    append(passengerUserId) // In đậm ID người lái
                    pop() // Kết thúc phần in đậm
                },
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Cost: $cost ", // Sử dụng tham số cost
                fontSize = 15.sp,
            )
            Text(
                text = "Ké Coins",
                fontSize = 15.sp,
                color = Color.Yellow
            )
        }
    }
}