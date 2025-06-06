package com.example.projectse104.ui.screens.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.projectse104.R
import com.example.projectse104.Component.*
import com.example.projectse104.core.Response
import com.example.projectse104.core.showToastMessage
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.profile.Component.*
import com.example.projectse104.utils.UriUtils
import java.util.UUID

@Composable
fun EditProfileScreen(
    navController: NavController,
    userId: String,
    formViewModel: UserDataViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val formState = formViewModel.state
    val profileState by profileViewModel.userState.collectAsState()
    val avatarUrl by profileViewModel.avatarUrl.collectAsState()
    val isLoading by profileViewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val id = UUID.randomUUID()

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        Log.d("EditProfileScreen", "Image URI selected: $uri")
        uri?.let {
            // Hiển thị ảnh ngay lập tức với URI
            formViewModel.onEvent(UserDataFormEvent.ProfilePicSelected(it.toString()))

            // Lấy đường dẫn thực tế để upload (bất đồng bộ)
            coroutineScope.launch {
                val filePath = UriUtils.getFileFromUri(context, it)
                if (filePath != null) {
                    // Upload với đường dẫn thực tế
                    formViewModel.uploadProfilePictureWithPath(filePath)
                } else {
                    showToastMessage(context, "Không thể xử lý ảnh: Không thể truy cập tệp")
                    // Reset lại state nếu không thể lấy đường dẫn
                    formViewModel.onEvent(UserDataFormEvent.ProfilePicSelected(""))
                }
            }
        }
    }

    // Chỉ cập nhật profilePicUri từ avatarUrl khi lần đầu load (không có ảnh local)
    LaunchedEffect(avatarUrl) {
        when (avatarUrl) {
            is Response.Success -> {
                val url = (avatarUrl as Response.Success<String>).data
                profileViewModel.fetchUserData(userId)
                Log.d("EditProfileScreen", "Avatar URL loaded: $url")
                // Chỉ set nếu chưa có ảnh được chọn
                if (formState.profilePicUri.isNullOrBlank()) {
                    formViewModel.setProfilePicUri(url)
                }
            }
            is Response.Failure -> {
                Log.e("EditProfileScreen", "Failed to load avatar: ${(avatarUrl as Response.Failure).e?.message}")
                if (formState.profilePicUri.isNullOrBlank()) {
                    formViewModel.setProfilePicUri(null)

                }
                showToastMessage(context, "Không thể tải ảnh đại diện")
            }
            else -> {
                Log.d("EditProfileScreen", "Avatar URL state: $avatarUrl")
            }
        }
    }

    // Cập nhật thông tin người dùng từ profileState
    LaunchedEffect(profileState) {
        when (val state = profileState) {
            is Response.Success -> {
                state.data?.profilePic?.let {
                    formViewModel.setProfilePicUri(it)
                    Log.d("EditProfileScreen", "ProfilePic set: $it")
                }
                state.data?.fullName?.let {
                    formViewModel.onEvent(UserDataFormEvent.FullNameChanged(it))
                    Log.d("EditProfileScreen", "FullName set: $it")
                }
                state.data?.email?.let {
                    formViewModel.onEvent(UserDataFormEvent.EmailChanged(it))
                    Log.d("EditProfileScreen", "Email set: $it")
                }
                state.data?.phoneNumber?.let {
                    formViewModel.onEvent(UserDataFormEvent.PhoneNumberChanged(it))
                    Log.d("EditProfileScreen", "PhoneNumber set: $it")
                }
                state.data?.location?.let {
                    formViewModel.onEvent(UserDataFormEvent.LocationChanged(it))
                    Log.d("EditProfileScreen", "Location set: $it")
                }
                profileViewModel.disableLoading()
            }
            is Response.Failure -> {
                Log.e("EditProfileScreen", "Failed to load user data: ${(state as Response.Failure).e?.message}")
                showToastMessage(context, "Không thể tải dữ liệu. Vui lòng thử lại!")
                profileViewModel.disableLoading()
            }
            is Response.Loading -> {
                Log.d("EditProfileScreen", "Loading user data...")
            }
            else -> {
                Log.d("EditProfileScreen", "Profile state: $state")
            }
        }
    }

    LaunchedEffect(key1 = context) {
        formViewModel.validationEvents.collect { event ->
            when (event) {
                is UserDataViewModel.ValidationEvent.Success -> {
                    showToastMessage(context, "Cập nhật hồ sơ thành công")
                    profileViewModel.fetchUserData(userId)
                }
                is UserDataViewModel.ValidationEvent.Error -> {
                    showToastMessage(context, "Lỗi: ${event.e?.message ?: "Không xác định"}")
                }
                UserDataViewModel.ValidationEvent.Loading -> {
                    // Không hiển thị toast loading ở đây để tránh spam
                }
            }
        }
    }

    val defaultAvatarID: Int = R.drawable.avatar_1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            BackArrowWithText(navController, "Chỉnh sửa hồ sơ")
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Avatar section with upload indicator
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Box(contentAlignment = Alignment.Center) {
                if (avatarUrl is Response.Loading && formState.profilePicUri.isNullOrBlank()) {
                    CircularProgressIndicator(modifier = Modifier.size(150.dp))
                } else {
                    AsyncImage(
                        model = formState.profilePicUri,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .clickable { imagePickerLauncher.launch("image/*") },
                        contentScale = ContentScale.Crop,
                    )
                    // Hiển thị loading indicator khi đang upload
                    if (formState.isUploadingProfilePic) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp),
                            color = Color.White,
                            strokeWidth = 3.dp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                enabled = !formState.isUploadingProfilePic,
            ) {
                Text(if (formState.isUploadingProfilePic) "Đang tải lên..." else "Chọn ảnh đại diện")
            }
        }

        if (formState.profilePicError != null) {
            Text(
                text = formState.profilePicError.toString(),
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        ProfileCustomTextFieldWithLabel("HỌ TÊN", formState.fullName) {
            formViewModel.onEvent(UserDataFormEvent.FullNameChanged(it))
        }
        ProfileCustomTextFieldWithLabel("EMAIL", formState.email) {
            formViewModel.onEvent(UserDataFormEvent.EmailChanged(it))
        }
        ProfileCustomTextFieldWithLabel("SỐ ĐIỆN THOẠI", formState.phoneNumber) {
            formViewModel.onEvent(UserDataFormEvent.PhoneNumberChanged(it))
        }
        ProfileCustomTextFieldWithLabel("ĐỊA CHỈ", formState.location) {
            formViewModel.onEvent(UserDataFormEvent.LocationChanged(it))
        }
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            BigButton(
                navController = navController,
                text = "LƯU THAY ĐỔI",
                onClick = { formViewModel.onEvent(UserDataFormEvent.Submit) }
            )
        }
    }

}