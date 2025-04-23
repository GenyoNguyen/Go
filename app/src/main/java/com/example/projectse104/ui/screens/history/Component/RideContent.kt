package com.example.projectse104.ui.screens.history.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
@Composable
fun RideContent(estimatedDeparture:String,
                fromLocation: String,
                toLocation: String,
                riderName: String,
                riderUserId: String,
                passengerName:String,
                passengerUserId: String,
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
                text = "Success", // Trạng thái cố định, bạn có thể thay đổi nếu cần
                fontSize = 15.sp,
                color = Color(0xFF35B82A)
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