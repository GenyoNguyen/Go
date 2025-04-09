package com.example.projectse104.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.*
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun EditProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {

    val userState by viewModel.userState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    var userId = ""
    var userFullName: String = "Lmao"
    var userGmail: String = "awegweagwaefweafwae"
    var location: String = ""
    var phoneNumber: String = ""
    var avatarID: Int = R.drawable.avatar_1
    var input_email by remember { mutableStateOf("") }
    var input_name by remember { mutableStateOf("") }
    var input_phoneNumber by remember { mutableStateOf("") }
    var input_location by remember { mutableStateOf("") }
    var loadingFailed:Boolean=false

    when (val state = userState) {
        is Response.Success<User> -> {
            userId = state.data?.id.toString()
            userFullName = state.data?.fullName?.toString() ?: ""
            userGmail = state.data?.email?.toString() ?: ""
            location = state.data?.location?.toString() ?: ""
            phoneNumber = state.data?.phoneNumber?.toString() ?: ""
            Log.d("EditProfileScreen", "Received")
            viewModel.disableLoading()
        }
        is Response.Loading -> {
        }
        else -> {
            loadingFailed=true
        }
    }
    ToastMessage(
        message = "Không thể tải dữ liệu. Vui lòng thử lại!",
        show = loadingFailed
    )
    if(isLoading) {
        ShimmerEditProfileScreen(navController)
    }
    else {
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
            BackArrowWithText(navController, "Edit Profile")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center) {
            Image(
                painter = painterResource(id = avatarID), // Ensure this is a valid drawable resource
                contentDescription = "Profile Avatar",
                modifier = Modifier.size(150.dp)
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        ProfileCustomTextFieldWithLabel("NAME", input_name, { input_name = it }, userFullName)
        ProfileCustomTextFieldWithLabel("EMAIL", input_email, { input_email = it }, userGmail)
        ProfileCustomTextFieldWithLabel(
            "PHONE NUMBER",
            input_phoneNumber,
            { input_phoneNumber = it },
            phoneNumber
        )
        ProfileCustomTextFieldWithLabel(
            "LOCATION",
            input_location,
            { input_location = it },
            location
        )
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            BigButton(
                navController = navController,
                text = "SAVE CHANGES",
                onClick = {})
        }
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController, userId, 4)

        }
    }
}