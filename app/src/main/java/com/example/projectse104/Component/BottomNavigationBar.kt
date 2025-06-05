package com.example.projectse104.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.valentinilk.shimmer.shimmer
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.projectse104.core.Response
@Composable
fun BottomNavigationBar(navController: NavController, userId: String, activate: Int) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
                           .height(60.dp),
        containerColor = Color.White // Nền trắng, không nổi bật

    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (activate == 1) R.drawable.home_icon_active else R.drawable.home_icon
                    ),
                    contentDescription = "Home",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Unspecified // Không thay màu icon
                )
            },
            selected = activate == 1,
            alwaysShowLabel = false, // ✅ Không hiển thị label
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent, // ✅ Không đổi nền khi chọn
                selectedIconColor = Color.Unspecified,
                unselectedIconColor = Color.Unspecified
            ),
            onClick = { navController.navigate("home/$userId") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (activate == 2) R.drawable.chat_icon_active else R.drawable.chat_icon
                    ),
                    contentDescription = "Chat",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Unspecified
                )
            },
            selected = activate == 2,
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.Transparent,
                unselectedIconColor = Color.Unspecified
            ),
            onClick = { navController.navigate("chat/$userId") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (activate == 3) R.drawable.history_icon_active else R.drawable.history_icon
                    ),
                    contentDescription = "History",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Unspecified
                )
            },
            selected = activate == 3,
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.Unspecified,
                unselectedIconColor = Color.Unspecified
            ),
            onClick = { navController.navigate("history/$userId") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (activate == 4) R.drawable.profile_icon_active else R.drawable.profile_icon
                    ),
                    contentDescription = "Profile",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Unspecified
                )
            },
            selected = activate == 4,
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.Unspecified,
                unselectedIconColor = Color.Unspecified
            ),
            onClick = { navController.navigate("profile/$userId") }
        )
    }
}