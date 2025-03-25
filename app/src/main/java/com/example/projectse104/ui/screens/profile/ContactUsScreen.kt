package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.*

@Composable
fun ContactUsScreen(navController: NavController, userId: String) {
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
                text = "Help & Support", // Sử dụng tham số rideNo
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Căn giữa theo chiều ngang
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp) // Căn giữa hoàn toàn
            )
        }
        Spacer(modifier=Modifier.height(20.dp).padding(horizontal=16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = {navController.navigate("help_support/$userId")},
                modifier = Modifier
                    .width(180.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDCF8EA)),
                contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
            ) {
                Text(
                    text = "FAQ",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
                )
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .width(180.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
                contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
            ) {
                Text(
                    text = "Contact Us",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
                )
            }
        }
        Spacer(modifier=Modifier.height(50.dp))
        Media(R.drawable.hsfacebook,"Facebook")
        Media(R.drawable.hswebsite,"Website")
        Spacer(modifier=Modifier.height(50.dp))
        Text(
            text = "Contact with Developer of this app",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier=Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier=Modifier.height(20.dp))

        Dev(R.drawable.phuc,"Nguyễn Xuân Phúc","23521213@gm.uit.edu.vn")
        Dev(R.drawable.triet,"Nguyễn Minh Triết","23521652@gm.uit.edu.vn")
        Dev(R.drawable.huy,"Nguyễn Phong Huy","23520637@gm.uit.edu.vn")

        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController, userId, 4)
    }
}
@Composable
fun Media(iconID:Int, name: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconID), // Ensure this is a valid drawable resource
                contentDescription = "Profile Avatar",
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier=Modifier.width((100.dp)))
            Text(
                    text = name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
        }
    }
}
@Composable
fun Dev(avatarResId: Int, devName: String,devGmail:String) {
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
                    text = devName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color=Color(0xFF7CCFA7)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = devGmail,
                    fontSize = 20.sp,
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