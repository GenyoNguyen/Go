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
fun RideItem(
    navController: NavController,
    rideNo: String = "",
    rideId: String = "",
    estimatedDeparture: String = "",
    fromLocation: String = "",
    toLocation: String = "",
    avatarResId: Int, // Thêm tham số avatarResId để truyền ảnh
    route: String = "", // Thêm tham số route
    userId: String = "",
    addGoButton: String = ""
) {
    var path: String = if (route == "ride_details") "$route/$userId/$rideId/$addGoButton"
    else if (route == "ride_details_history") "$route/$userId/$rideId"
    else "$route/$userId/$rideId"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp) // Độ dày của top border
                .background(Color(0xFF8FC79A)) // Màu của top border
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically, // Căn giữa ảnh theo chiều dọc trong Row
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Ride No. $rideNo", // Sử dụng tham số rideNo
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Căn giữa ảnh theo chiều dọc trong Row
                ) {
                    Text(
                        text = "Estimated Departure",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = estimatedDeparture, // Sử dụng tham số estimatedDeparture
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Căn giữa ảnh theo chiều dọc trong Row
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.path), // Đổi lại với icon của bạn
                        contentDescription = "Path Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "From: $fromLocation - To: $toLocation", // Sử dụng tham số từ and đến
                        color = Color(0xFF8FC79A),
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.width(15.dp))
            Image(
                painter = painterResource(id = avatarResId), // Sử dụng tham số avatarResId
                contentDescription = "Avatar",
                modifier = Modifier
                    .height(60.dp) // Chiều cao bằng 80% container của nó (phần tử cha)
                    .aspectRatio(1f) // Đảm bảo tỉ lệ 1:1 cho ảnh (vì bạn chỉ định chiều cao)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navController.navigate(path)
            },
            modifier = Modifier
                .width(80.dp)
                .height(24.dp)
                .align(Alignment.End), // Căn chỉnh button về bên phải
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
            contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
        ) {
            Text(
                text = "Details",
                fontSize = 13.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}