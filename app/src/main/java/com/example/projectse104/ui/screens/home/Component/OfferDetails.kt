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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.valentinilk.shimmer.shimmer
import java.util.Calendar
import com.example.projectse104.ui.screens.home.Component.*
@Composable
fun OfferDetails(estimatedDeparture: String,
                 fromLocation: String,
                 toLocation: String,
                 riderName:String,
                 riderUserId:String,
                 cost:String){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$estimatedDeparture",
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Route: From $fromLocation to $toLocation",
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Rider: ",
                fontSize = 15.sp
            )
            Text(
                text = "$riderName",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "- (UserID: $riderUserId)",
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
                text = "Success",
                fontSize = 15.sp,
                color = Color.Green
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Passenger Information: ???",
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Cost: $cost ",
                fontSize = 15.sp,
            )
            Text(
                text = "Ké Coins",
                fontSize = 15.sp,
                color = Color.Yellow
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Potential Passengers Section
        // Potential Passengers Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Potential Passengers",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa chữ và đường kẻ
            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f) // Chiếm toàn bộ không gian còn lại của Row để kéo dài đường kẻ
            )
        }
    }
}