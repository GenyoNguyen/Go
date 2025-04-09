package com.example.projectse104.ui.screens.history

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
fun OverviewRating(navController: NavController,
                   state:String,
                   userId:String,
                   rideNo:String, ){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {if (state=="rating")navController.popBackStack() else{} },
            modifier = Modifier
                .width(180.dp)
                .height(30.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(if(state=="overview") 0xFF8FC79A else 0xFFDCF8EA)),
            contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
        ) {
            Text(
                text = "Overview",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
            )
        }

        Button(
            onClick = {if (state=="overview") navController.navigate("ride_details_rating/$userId/$rideNo") else {} },
            modifier = Modifier
                .width(180.dp)
                .height(30.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(if(state=="rating") 0xFF8FC79A else 0xFFDCF8EA)),
            contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
        ) {
            Text(
                text = "Rating",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
            )
        }
    }
}
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
@Composable
fun ratingStars(rating:Int){
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
}
@Composable
fun RatingContent(riderAvatarId:Int,
                  trustScore:Int,
                  keCoins:Int,
                  riderName:String,
                  riderUserId:String,
                  content:String){
    Row(modifier = Modifier.offset(x = 53.dp)) {
        Image(
            painter = painterResource(id = riderAvatarId), // Rider's avatar image
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
}