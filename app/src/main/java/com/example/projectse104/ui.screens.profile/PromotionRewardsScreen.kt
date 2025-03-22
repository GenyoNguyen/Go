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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.ui.screens.home.BottomNavigationBar

@Composable
fun PromotionRewardsScreen(navController: NavController, userName: String) {
    var keCoins:String="2909"
    var avatarID:Int=R.drawable.avatar_1
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
                text = "Promotion & Rewards", // Sử dụng tham số rideNo
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Căn giữa theo chiều ngang
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp) // Căn giữa hoàn toàn
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier=Modifier.fillMaxWidth(),Arrangement.Center) {
            Image(
                painter = painterResource(id = avatarID), // Ensure this is a valid drawable resource
                contentDescription = "Profile Avatar",
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFFFC800))
        ){
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.Start){
                Text(
                    text="Ké coins",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                Text(
                    text=keCoins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                Image(
                    painter = painterResource(id = R.drawable.coins_icon), // Ensure this is a valid drawable resource
                    contentDescription = "Profile Avatar",
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFDCF8EA))
        ){
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.redeem_code_icon), // Ensure this is a valid drawable resource
                    contentDescription = "Profile Avatar",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier=Modifier.width(5.dp))
                Text(
                    text="Redeem Code",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                InputhBar()
                Spacer(modifier = Modifier.width(5.dp))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .height(50.dp), // Add padding on the right side
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEFF8F2))
                ) {
                    Text(
                        text = "Redeem",
                        fontSize = 14.sp,
                        color = Color.Green,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController, userName, 4)

    }
}
@Composable
fun InputhBar() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) } // Quản lý trạng thái nhập văn bản

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .padding(horizontal = 16.dp)
            .height(50.dp) // Tăng nhẹ chiều cao để có thêm không gian hiển thị
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFEFF8F2)),
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp // Đảm bảo dòng chữ không bị cắt
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFEFF8F2),
            unfocusedContainerColor = Color(0xFFEFF8F2),
            disabledContainerColor = Color(0xFFEFF8F2),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}