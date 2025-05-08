package com.example.projectse104.ui.screens.profile

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.BackArrowWithText
import com.example.projectse104.BottomNavigationBar
import com.example.projectse104.R
import com.example.projectse104.ToastMessage
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.profile.Component.*

@Composable
fun PromotionRewardsScreen(navController: NavController, userId: String) {
    var keCoins: String = "2909"
    var avatarID: Int = R.drawable.avatar_1
    var redeemCode by remember { mutableStateOf("") } // Quản lý trạng thái nhập văn bản
    var isLoading: Boolean = true
    var loadingFailed: Boolean = false
    val state: Response<User> = Response.Success(
        User(
            id = userId, fullName = "Nguyễn Xuân Phúc",
            email = "nguyenxuanphuc010205@gmail.com",
            profilePic = R.drawable.avatar.toString(),
            overallRating = 5.0f, userCode = "kzdf2",
            coins = keCoins.toInt()
        )
    )
    when (state) {
        is Response.Success<User> -> {
            avatarID = state.data?.profilePic?.toIntOrNull() ?: R.drawable.avatar
            keCoins = state.data?.coins.toString()
            isLoading = false
            loadingFailed = false
        }

        is Response.Loading -> {
            isLoading = true
        }

        else -> {
            loadingFailed = true
        }
    }
    ToastMessage(
        message = "Không thể tải dữ liệu. Vui lòng thử lại!",
        show = loadingFailed
    )
    if (isLoading) {
        ShimmerPromotionRewardsScreen(navController)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            BackArrowWithText(navController, "Promotion & Rewards")

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center) {
                Image(
                    painter = painterResource(id = avatarID), // Ensure this is a valid drawable resource
                    contentDescription = "Profile Avatar",
                    modifier = Modifier.size(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            KeCoinsDisplay(keCoins)

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFDCF8EA))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.redeem_code_icon), // Ensure this is a valid drawable resource
                        contentDescription = "Profile Avatar",
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Redeem Code",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Vùng nhập redeem code
                    RedeemCodeInputhBar(redeemCode, { redeemCode = it })

                    Spacer(modifier = Modifier.width(5.dp))
                    Button(
                        onClick = {
                            //Hành động khi nhấn redeem
                        },
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
            BottomNavigationBar(navController, userId, 4)
        }
    }
}
