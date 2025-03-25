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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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

@Composable
fun OverviewRating(navController: NavController,
                   state:String,
                   userId:String,
                   rideNo:String,
                   riderName:String,
                   riderUserId:String,
                   fromLocation:String,
                   toLocation:String){
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
            onClick = {if (state=="overview") navController.navigate("ride_details_rating/$userId/$rideNo/$riderName/$riderUserId/$fromLocation/$toLocation") else {} },
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
                text = "Rider: ",
                fontSize = 15.sp
            )
            Text(
                text = riderName, // Sử dụng tham số riderName
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "- (UserID: $riderUserId)", // Sử dụng tham số riderUserId
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
                text = "Passenger Information: ",
                fontSize = 15.sp
            )
            Text(
                text = passengerName, // Sử dụng tham số passengerName
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "- (UserID: $passengerUserId)", // Sử dụng tham số passengerUserId
                fontSize = 15.sp,
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