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
fun ProfileViewScreen(navController: NavController, userName: String) {
    var userFullName: String = "Nguyễn Xuân Phúc"
    var rating:String="4.5"
    var postition:String="Dĩ An, Bình Dương"
    var ridesTaken:String="27"
    var ridesGiven:String="36"
    var trustScore:String="209"
    var avatarID:Int=R.drawable.avatar_1
    var accompanies:List<List<Any>> = listOf(
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
                text = "Profile", // Sử dụng tham số rideNo
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Căn giữa theo chiều ngang
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp) // Căn giữa hoàn toàn
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Thêm padding để căn chỉnh
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically// Căn trái để mũi tên ở góc trái
        ) {
            Image(
                painter = painterResource(id = avatarID), // Ensure this is a valid drawable resource
                contentDescription = "Profile Avatar",
                modifier = Modifier
                    .size(150.dp)
            )
            Column {
                Text(
                    text = userFullName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Active member",
                    fontSize = 18.sp,
                    color = Color(0xFF7CCFA7)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Rating: $rating/5",
                    fontSize = 18.sp,
                    color = Color(0xFFBEB204),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(){
                    Icon(
                        painter = painterResource(id = R.drawable.profile_view_location), // Icon for navigation
                        contentDescription = "Arrow Icon",
                        modifier = Modifier.size(20.dp),
                        tint=Color(0xFF544C44)
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = postition,
                        fontSize = 18.sp,
                        color = Color(0xFF544C44)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp), // Thêm padding để căn chỉnh
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically// Căn trái để mũi tên ở góc trái
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = ridesTaken,
                    fontSize = 24.sp,
                    color = Color(0xFF242760),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Rides taken",
                    fontSize = 18.sp,
                    color = Color(0xFF544C44),
                    textAlign = TextAlign.Center
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = ridesGiven,
                    fontSize = 24.sp,
                    color = Color(0xFF242760),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Rides given",
                    fontSize = 18.sp,
                    color = Color(0xFF544C44),
                    textAlign = TextAlign.Center
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = trustScore,
                    fontSize = 24.sp,
                    color = Color(0xFF242760),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Trust Score",
                    fontSize = 18.sp,
                    color = Color(0xFF544C44),
                    textAlign = TextAlign.Center
                )
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent activity",
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
        for (accompany in accompanies){
            var avatarResId: Int = when (val value = accompany[0]) {
                is Int -> value
                is String -> value.toIntOrNull() ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                else -> 0 // Nếu không phải Int hoặc String, trả về 0
            }
            var accompanyName:String=accompany[1].toString()
            RecentAccompany(avatarResId,accompanyName)
        }
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController, userName, 4)
    }
}
@Composable
fun RecentAccompany(
    avatarResId: Int,
    passengerName: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal=16.dp) // Đảm bảo column chiếm hết chiều rộng
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Căn chỉnh và thêm khoảng cách giữa các dòng
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Avatar
            Image(
                painter = painterResource(id = avatarResId), // Avatar người dùng
                contentDescription = "Passenger Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(25.dp)) // Bo tròn ảnh avatar
            )
            Spacer(modifier = Modifier.width(50.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Go with",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = passengerName,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        // Đường đứt nét ở cuối
        Divider(
            color = Color(0xFF8FC79A),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth() // Đảm bảo đường đứt nét dài bằng container
                .padding(top = 8.dp), // Khoảng cách giữa phần trên và đường đứt nét
        )
    }
}
