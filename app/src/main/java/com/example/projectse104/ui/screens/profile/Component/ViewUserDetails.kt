package com.example.projectse104.ui.screens.profile.Component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projectse104.R
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.rememberShimmer
@Composable
fun ViewUserDetails(
    profilePicUrl: String?,
    userFullName: String,
    rating: String,
    position: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp), // Thêm padding dọc để thoáng hơn
        horizontalArrangement = Arrangement.Start, // Đặt căn trái để các phần tử gọn gàng
        verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
    ) {
        // Hình ảnh avatar
        AsyncImage(
            model = profilePicUrl,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color(0xFFE0E0E0), CircleShape) // Thêm viền nhẹ cho avatar
                .clickable { /* Có thể thêm logic để mở image picker nếu cần */ },
            contentScale = ContentScale.Crop
        )

        // Khoảng cách giữa avatar và thông tin
        Spacer(modifier = Modifier.width(40.dp))

        // Cột chứa thông tin người dùng
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp), // Thêm padding dọc cho cột
            verticalArrangement = Arrangement.spacedBy(8.dp) // Khoảng cách đều giữa các Text
        ) {
            // Tên người dùng
            Text(
                text = userFullName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            // Trạng thái thành viên
            Text(
                text = "Active member",
                fontSize = 16.sp, // Giảm nhẹ font để cân đối
                color = Color(0xFF7CCFA7),
                fontWeight = FontWeight.Medium
            )

            // Điểm đánh giá
            Text(
                text = "Rating: ${String.format("%.1f", rating.toDouble())}/5.0",
                fontSize = 16.sp,
                color = Color(0xFFBEB204),
                fontWeight = FontWeight.SemiBold
            )

            // Vị trí
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.profile_view_location),
                    contentDescription = "Location Icon",
                    modifier = Modifier.size(18.dp), // Giảm kích thước icon cho cân đối
                    tint = Color(0xFF544C44)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = position,
                    fontSize = 16.sp,
                    color = Color(0xFF544C44)
                )
            }
        }
    }
}