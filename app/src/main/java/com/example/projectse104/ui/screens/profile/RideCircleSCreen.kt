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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.*

@Composable
fun RideCircleScreen(navController: NavController, userId: String) {
    var ridesTaken:String="27"
    var ridesGiven:String="36"
    var trustScore:String="209"
    var avatarID:Int=R.drawable.avatar_1
    var riders:List<List<Any>> = listOf(
        listOf(R.drawable.avatar_1,"Nguyễn Hữu Dũng","0001"),
        listOf(R.drawable.avatar_2,"Nguyễn Phong Huy","0002"),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        BackArrowWithText(navController,"My Ride Circle")

        Spacer(modifier = Modifier.height(20.dp))

        RideCircleDetails(avatarID,
                        trustScore,
                        ridesTaken,
                        ridesGiven)

        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your favourite rider",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa chữ và đường kẻ
            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f) // Chiếm toàn bộ không gian còn lại của Row để kéo dài đường kẻ
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        for (rider in riders){
            var avatarResId: Int = when (val value = rider[0]) {
                is Int -> value
                is String -> value.toIntOrNull() ?: 0  // Nếu giá trị là String, cố gắng chuyển đổi, nếu không trả về 0
                else -> 0 // Nếu không phải Int hoặc String, trả về 0
            }
            var accompanyName:String=rider[1].toString()
            var conversationId:String=rider[2].toString()
            favouriteRider(navController,userId,accompanyName,avatarResId,conversationId)
        }
        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController, userId, 4)
    }
}