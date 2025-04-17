package com.example.projectse104.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projectse104.BackArrowWithText
import com.example.projectse104.BigButton
import com.example.projectse104.BottomNavigationBar
import com.example.projectse104.R
import com.example.projectse104.ToastMessage
import com.example.projectse104.core.Response
import com.example.projectse104.core.showToastMessage
import com.example.projectse104.domain.model.User


@Composable
fun EditProfileScreen(
    navController: NavController,
    userId: String,
    formViewModel: UserDataViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val formState = formViewModel.state
    val profileState by profileViewModel.userState.collectAsState()
    val isLoading by profileViewModel.isLoading.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        formViewModel.validationEvents.collect { event ->
            when (event) {
                is UserDataViewModel.ValidationEvent.Success -> {
                    showToastMessage(context, "Profile updated successfully")
                }

                is UserDataViewModel.ValidationEvent.Error -> {
                    showToastMessage(context, "Error: ${event.e?.message.toString()}")
                }

                UserDataViewModel.ValidationEvent.Loading -> {
                    showToastMessage(context, "Loading...")
                }
            }
        }
    }

    val avatarID: Int = R.drawable.avatar_1

    if (isLoading) {
        when (val state = profileState) {
            is Response.Success<User> -> {
                state.data?.fullName?.let {
                    formViewModel.onEvent(UserDataFormEvent.FullNameChanged(it))
                }
                state.data?.email?.let {
                    formViewModel.onEvent(UserDataFormEvent.EmailChanged(it))
                }
                state.data?.phoneNumber?.let {
                    formViewModel.onEvent(UserDataFormEvent.PhoneNumberChanged(it))
                }
                state.data?.location?.let {
                    formViewModel.onEvent(UserDataFormEvent.LocationChanged(it))
                }
                Log.d("EditProfileScreen", "Received")
                profileViewModel.disableLoading()
            }

            is Response.Failure -> {
                ToastMessage(
                    message = "Không thể tải dữ liệu. Vui lòng thử lại!",
                    show = true
                )
            }

            else -> {}
        }
    }


    if (isLoading) {
        ShimmerEditProfileScreen(navController)
    } else {
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
            ProfileCustomTextFieldWithLabel("NAME", formState.fullName) {
                formViewModel.onEvent(UserDataFormEvent.FullNameChanged(it))
            }
            ProfileCustomTextFieldWithLabel("EMAIL", formState.email) {
                formViewModel.onEvent(UserDataFormEvent.EmailChanged(it))
            }
            ProfileCustomTextFieldWithLabel("PHONE NUMBER", formState.phoneNumber) {
                formViewModel.onEvent(UserDataFormEvent.PhoneNumberChanged(it))
            }
            ProfileCustomTextFieldWithLabel("LOCATION", formState.location) {
                formViewModel.onEvent(UserDataFormEvent.LocationChanged(it))
            }
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                BigButton(
                    navController = navController,
                    text = "SAVE CHANGES",
                    onClick = { formViewModel.onEvent(UserDataFormEvent.Submit) })
            }
            Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
            BottomNavigationBar(navController, userId, 4)

        }
    }
}