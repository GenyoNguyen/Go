package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.core.showToastMessage
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.profile.Component.HeaderChangeSection
import com.example.projectse104.ui.screens.profile.Component.ProfileHeader
import com.example.projectse104.ui.screens.profile.Component.ProfileOption
import com.example.projectse104.ui.screens.profile.Component.ShimmerProfileScreen
import com.example.projectse104.utils.DataStoreSessionManager
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ProfileScreen(
    navController: NavController,
    userId: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    sessionManager: DataStoreSessionManager
) {
    val userState by viewModel.userState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.fetchUserData(userId)
    }

    var userFullName = "Unknown"
    var userGmail = "Unknown"
    var userCode = "Unknown"
    val userAvatarId: Int = R.drawable.avatar
    val avatarUrl = viewModel.avatarUrl.collectAsState().value
    var profilePicUrl: String? = null

    when (val state = userState) {
        is Response.Success<User> -> {
            state.data?.let { user ->
                userCode = user.userCode
                userFullName = user.fullName
                userGmail = user.email
            }
            viewModel.disableLoading()
        }
        is Response.Failure -> {
            ToastMessage(
                message = "Không thể tải dữ liệu. Vui lòng thử lại!",
                show = true
            )
        }
        else -> {}
    }
    when (avatarUrl) {
        is Response.Success<String> -> {
            profilePicUrl = avatarUrl.data
        }
        is Response.Failure -> {
            profilePicUrl = null
        }
        else -> {
            profilePicUrl = null
        }
    }

    if (isLoading) {
        ShimmerProfileScreen(navController, userId)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            ProfileHeader()
            Column(modifier = Modifier.offset(y = (-70).dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .clickable { navController.navigate("profile_view/$userId/false") }
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        HeaderChangeSection(
                            navController,
                            userAvatarId,
                            userFullName,
                            userGmail,
                            userCode,
                            userId,
                            profilePicUrl
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    ProfileOption(
                        navController = navController,
                        title = "Saved Locations",
                        avatarID = R.drawable.profile_icon_1,
                        route = "saved_locations/$userId"
                    )
                    ProfileOption(
                        navController = navController,
                        title = "Promotions & Rewards",
                        avatarID = R.drawable.profile_icon_2,
                        route = "promotion_rewards/$userId"
                    )
                    ProfileOption(
                        navController = navController,
                        title = "My Ride Circle",
                        avatarID = R.drawable.profile_icon_3,
                        route = "ride_circle/$userId"
                    )
                    ProfileOption(
                        navController = navController,
                        title = "Help & Support",
                        avatarID = R.drawable.profile_icon_4,
                        route = "help_support/$userId"
                    )
                    ProfileOption(
                        navController = navController,
                        title = "Sign out",
                        avatarID = R.drawable.profile_icon_5,
                        route = "sign_in",
                        onClick = {
                            coroutineScope.launch {
                                sessionManager.clearSession()
                                showToastMessage(context, "Đăng xuất thành công")
                                navController.navigate("sign_in") {
                                    popUpTo("home/$userId") { inclusive = true }
                                    popUpTo("profile/$userId") { inclusive = true }
                                }
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            BottomNavigationBar(navController, userId, 4)
        }
    }
}