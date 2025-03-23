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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp), // Thêm padding để căn chỉnh
            verticalAlignment = Alignment.CenterVertically, // Căn giữa theo chiều dọc
            horizontalArrangement = Arrangement.Start // Căn trái để mũi tên ở góc trái
        ) {
            // Mũi tên quay lại
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "Details of Ride No. $rideID", // Sử dụng tham số rideNo
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Căn giữa theo chiều ngang
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp) // Căn giữa hoàn toàn
            )
        }
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
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
        ) {
            Button(
                onClick = {navController.navigate("offer_a_ride/$riderName")
                }, // Điều hướng về màn hình FindARide
                modifier = Modifier
                    .width(150.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "NO",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {navController.navigate("booking_successful/$riderName")
                }, // Điều hướng tới màn hình BookingSuccessful
                modifier = Modifier
                    .width(150.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "YES!",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
