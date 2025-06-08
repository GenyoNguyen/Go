package com.example.projectse104.Component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectse104.R

@Composable
fun BottomNavigationBar(
    navController: NavController,
    userId: String,
    activate: Int,
    messageCount: Int = 0
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        containerColor = Color.White // Nền trắng, không nổi bật

    ) {
        NavigationBarItem(
            icon = {
                BadgedBox(
                    badge = {
                        if (messageCount > 0) {
                            Badge {
                                Text(text = messageCount.toString())
                            }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (activate == 1) R.drawable.home_icon_active else R.drawable.home_icon
                        ),
                        contentDescription = "Home",
                        modifier = Modifier.size(80.dp),
                        tint = Color.Unspecified // Không thay màu icon
                    )
                }
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