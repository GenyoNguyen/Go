package com.example.projectse104.ui.screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.example.projectse104.ui.screens.home.Component.*

@Composable
fun ConfirmRequestScreen(
    navController: NavController,
    passengerName: String, // Thêm tham số userName
    rideID: String,
    riderName:String// Thêm tham số rideID
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BackArrowWithText(navController,"Details of Ride No. $rideID")

        Spacer(modifier = Modifier.height(100.dp))

        // Image for the tick icon in the center
        Image(
            painter = painterResource(id = R.drawable.confirm_ride), // Thay thế bằng ID của hình ảnh dấu tick của bạn
            contentDescription = "Tick Icon",
            modifier = Modifier.size(200.dp) // Điều chỉnh kích thước nếu cần
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Information Text with bold parts for userName and rideID
        val text = buildAnnotatedString {
            append("By clicking the YES button, ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(passengerName)
            }
            append(" will join you on the upcoming Ride No. ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(rideID)
            }
            append(". Do you agree?")
        }

        Text(
            text = text,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons (NO and YES)
        YesNoButtons(navController,
            yesOnClick = {navController.navigate("booking_successful/$riderName")},
            noOnClick = {navController.navigate("offer_a_ride/$riderName")})
    }
}
