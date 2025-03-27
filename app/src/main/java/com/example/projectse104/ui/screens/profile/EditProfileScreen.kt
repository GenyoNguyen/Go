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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.*

@Composable
fun EditProfileScreen(navController: NavController, userId: String) {
    var userFullName: String = "Nguyễn Xuân Phúc"
    var userGmail: String = "nguyenxuanphuc010205@gmail.com"
    var location:String="Thủ Đức"
    var phoneNumber:String="+84 399879888"
    var avatarID:Int=R.drawable.avatar_1
    var input_email by remember { mutableStateOf("") }
    var input_name by remember { mutableStateOf("") }
    var input_phoneNumber by remember { mutableStateOf("") }
    var input_location by remember { mutableStateOf("") }
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
            BackArrowWithText(navController,"Edit Profile")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier=Modifier.fillMaxWidth(),Arrangement.Center) {
            Image(
                painter = painterResource(id = avatarID), // Ensure this is a valid drawable resource
                contentDescription = "Profile Avatar",
                modifier = Modifier.size(150.dp)
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        ProfileCustomTextFieldWithLabel("NAME",input_name,{input_name=it},userFullName)
        ProfileCustomTextFieldWithLabel("EMAIL",input_email,{input_email=it},userGmail)
        ProfileCustomTextFieldWithLabel("PHONE NUMBER",input_phoneNumber,{input_phoneNumber=it},phoneNumber)
        ProfileCustomTextFieldWithLabel("LOCATION",input_location,{input_location=it},location)
        Row(modifier = Modifier.padding(horizontal=16.dp)) {
            BigButton(navController = navController,
                text = "SAVE CHANGES",
                onClick = {})
        }
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController, userId, 4)

    }
}