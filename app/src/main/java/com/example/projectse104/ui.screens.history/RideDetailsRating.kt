package com.example.projectse104.ui.screens.history


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import com.example.projectse104.ui.screens.home.BottomNavigationBar
@Composable
fun RideDetailsRatingScreen(
    navController: NavController,
    userName: String,
    rideNo: String,
    riderName: String,
    riderUserId: String,
    fromLocation:String,
    toLocation:String
) {
    var rating: Int = 5
    var trustScore: Int = 36
    var keCoins: Int = 103
    var content:String="The ride from Dĩ An to District 1 was very smooth. The driver, Nguyễn Hữu Dũng, was friendly, professional, and drove safely. Although the journey was quite long, he maintained a steady speed, making the ride comfortable and pleasant."

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
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
                text = "Details of Ride No. $rideNo", // Sử dụng tham số rideNo
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Header with tabs (Overview, Rating)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {navController.popBackStack()},
                modifier = Modifier
                    .width(180.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDCF8EA)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Overview",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .width(180.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Rating",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Rating and feedback section
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center // Căn giữa Row trong Box
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.8f), // Điều chỉnh độ rộng của Row (80% của chiều rộng màn hình)
                horizontalArrangement = Arrangement.SpaceBetween // Sử dụng SpaceBetween trong Row
            ) {
                // Display the 5 stars based on the rating value
                repeat(rating) {
                    Image(
                        painter = painterResource(id = R.drawable.star_icon), // Replace with the actual star image resource
                        contentDescription = "Star",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        // Rider Info
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.offset(x = 53.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.avatar), // Rider's avatar image
                    contentDescription = "Rider Avatar",
                    modifier = Modifier
                        .size(125.dp)
                        .clip(CircleShape)
                )
                Column() {
                    Text(
                        text = "+$trustScore trust scores",
                        fontSize = 14.sp,
                        color = Color(0xFF35B82A),
                        modifier = Modifier.offset(x = -10.dp)
                    )
                    Text(
                        text = "+$keCoins Ké coins",
                        fontSize = 14.sp,
                        color = Color(0xFF35B82A)
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            Column {
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
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = content,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start // Căn chỉnh từ bên trái
            ) {
                // Text với phần clickable được xây dựng bằng AnnotatedString
                Text(
                    text = buildAnnotatedString {
                        append("Want to contact again with ")
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold)) // In đậm tên tài xế
                        append(riderName) // In đậm tên tài xế
                        pop() // Kết thúc phần in đậm
                        append(", ")
                        pushStyle(SpanStyle(color = Color(0xFF35B82A))) // Đổi màu xanh cho phần "click here"
                        append("click here") // Phần clickable
                        pop() // Đóng phần style
                    },
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("chat_details/$userName/$riderName/yes")
                        }
                )
            }


        }
        }
}
