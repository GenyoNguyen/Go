package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.screens.home.BottomNavigationBar

@Composable
fun RideCircleScreen(navController: NavController, userName: String) {
    var ridesTaken:String="27"
    var ridesGiven:String="36"
    var trustScore:String="209"
    var avatarID:Int=R.drawable.avatar_1
    var riders:List<List<Any>> = listOf(
        listOf(R.drawable.avatar_1,"Nguyễn Hữu Dũng"),
        listOf(R.drawable.avatar_2,"Nguyễn Phong Huy"),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                text = "My Ride Circle", // Sử dụng tham số rideNo
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Căn giữa theo chiều ngang
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp) // Căn giữa hoàn toàn
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier=Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)){
            Text(
                text = "Your trust score",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Thêm padding để căn chỉnh
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically// Căn trái để mũi tên ở góc trái
        ) {
            Image(
                painter = painterResource(id = avatarID), // Ensure this is a valid drawable resource
                contentDescription = "Profile Avatar",
                modifier = Modifier
                    .size(100.dp)
            )
            Spacer(modifier=Modifier.width(20.dp))
            Column(modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFDCF8EA))
                .border(
                    1.dp,
                    Color(0XFF7CCFA7),
                    RoundedCornerShape(8.dp)

                )
                .padding(16.dp)
            ){
                Text(
                    text = "Trust score: $trustScore",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0XFF7CCFA7)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Total Rides Taken: $ridesTaken",
                    fontSize = 22.sp,
                    color = Color(0xFF094DE0),
                    fontWeight = FontWeight.Bold,

                    )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Total Rides Given: $ridesGiven",
                    fontSize = 22.sp,
                    color = Color(0xFFF4B3B3),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your favourite rider",
                fontSize = 20.sp,
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
        Spacer(modifier = Modifier.height(20.dp))
        for (rider in riders){
            var avatarResId: Int = when (val value = rider[0]) {
                is Int -> value
                is String -> value.toIntOrNull() ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                else -> 0 // Nếu không phải Int hoặc String, trả về 0
            }
            var accompanyName:String=rider[1].toString()
            favouriteRider(navController,userName,accompanyName,avatarResId)
        }
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController, userName, 4)
    }
}
@Composable
fun favouriteRider(navController: NavController,userName:String,riderName:String,avatarID:Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = avatarID), // Ensure this is a valid drawable resource
                contentDescription = "Profile Avatar",
                modifier = Modifier
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                text = riderName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.chat_nav_icon), // Icon for navigation
                contentDescription = "Arrow Icon",
                modifier = Modifier.size(20.dp).clickable { navController.navigate("chat_details/$userName/$riderName/yes") },
            )
        }
        Spacer(modifier=Modifier.height(5.dp))
        Divider(color = Color(0XFF35B82A), thickness = 1.dp)
    }
}
