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
import com.example.projectse104.Component.*
import com.example.projectse104.ui.screens.profile.Component.*
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
            IconButton(onClick = { navController.navigate("profile/$userId") }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "Help & Support", // Sử dụng tham số rideNo
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Căn giữa theo chiều ngang
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp) // Căn giữa hoàn toàn
            )
        }
        Spacer(modifier=Modifier.height(20.dp).padding(horizontal=16.dp))

        FAQContacUS(navController,userId,"contact")

        Spacer(modifier=Modifier.height(50.dp))

        Media(iconID = R.drawable.hsfacebook,
            name = "Facebook",
            url="https://www.facebook.com/"
            )

        Media(iconID =R.drawable.hswebsite,
            name="Website",
            url="https://www.google.com/?hl=vi")

        Spacer(modifier=Modifier.height(50.dp))
        Text(
            text = "Contact with Developer of this app",
            fontSize = 19.sp,
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
