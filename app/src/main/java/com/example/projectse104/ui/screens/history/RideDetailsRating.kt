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
import com.example.projectse104.*
@Composable
fun RideDetailsRatingScreen(
    navController: NavController,
    userId: String,
    rideNo: String,
) {
    var riderName: String="Nguyễn Hữu Dũng"
    var riderUserId: String="10000512"
    var riderAvatarId:Int=R.drawable.avatar_1
    var rating: Int = 5
    var trustScore: Int = 36
    var keCoins: Int = 103
    var content:String="The ride from Dĩ An to District 1 was very smooth. The driver, Nguyễn Hữu Dũng, was friendly, professional, and drove safely. Although the journey was quite long, he maintained a steady speed, making the ride comfortable and pleasant."
    //Id hội thoại tương ứng với chuyến đi
    var conversationId:String="0001"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Mũi tên quay lại
            IconButton(onClick = { navController.navigate("history/$userId") }) {
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

        OverviewRating(navController,
            state="rating",
            userId,
            rideNo,)

        Spacer(modifier = Modifier.height(20.dp))

        // Rating and feedback section
        ratingStars(rating)


        Spacer(modifier = Modifier.height(20.dp))

        // Rider Info
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RatingContent(riderAvatarId,
                trustScore,
                keCoins,
                riderName,
                riderUserId,
                content)


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
                            navController.navigate("chat_details/$userId/$conversationId")
                        }
                )
            }


        }
        }
}
