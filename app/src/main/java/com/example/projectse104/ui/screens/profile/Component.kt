package com.example.projectse104.ui.screens.profile

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.*
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun ProfileHeader(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(8.dp)) // Bo tròn 4 góc của header
            .background(Color(0xFF8FC79A)), // Thêm background sau khi clip
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(45.dp))                // Row cho Home và Hi, {userName}
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start, // Tạo khoảng cách giữa "Home" và "Hi, {userName}"
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thêm icon ngôi nhà cạnh chữ Home
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Profile",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và chữ "Home"
                Image(
                    painter = painterResource(id = R.drawable.user_icon), // Đổi lại với icon của bạn
                    contentDescription = "Home Icon",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(45.dp))                // Row cho Home và Hi, {userName}

    }
}
@Composable
fun ProfileOption(navController: NavController,title: String,avatarID:Int,route:String="") {
    Column(modifier = Modifier.fillMaxWidth().clickable{navController.navigate(route)}) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = avatarID), // Icon for navigation
                contentDescription = "Arrow Icon",
                modifier = Modifier.size(20.dp),
                tint=Color.Gray
            )
            Spacer(modifier=Modifier.width((10.dp)))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right), // Icon for navigation
                contentDescription = "Arrow Icon",
                modifier = Modifier.size(20.dp),
                tint=Color.Gray
            )
        }
        Spacer(modifier=Modifier.height(5.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}
@Composable
fun HeaderChangeSection(navController: NavController,
                        userAvatarId:Int,
                        userFullName:String,
                        userGmail:String,
                        userId:String){
    Image(
        painter = painterResource(id = userAvatarId), // Đổi lại với icon của bạn
        contentDescription = "Home Icon",
        modifier = Modifier.size(70.dp)
    )
    Column() {
        Text(
            text = userFullName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = userGmail,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "UserID: $userId",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
    Image(
        painter = painterResource(id = R.drawable.change_icon), // Đổi lại với icon của bạn
        contentDescription = "Home Icon",
        modifier = Modifier.size(30.dp)
            .clickable {navController.navigate("edit_profile/$userId")} // Navigate to page2 when change icon is clicked

    )
}
@Composable
fun ProfileCustomTextFieldWithLabel(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    remain_value: String = "" // Add default value parameter
) {
    val focusedColor = Color(0xFF8FC79A)

    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (value.isNotEmpty()) focusedColor else Color.Gray,
            modifier = Modifier
                .padding(start = 16.dp)
                .offset(y = -7.dp)
                .zIndex(1f)
        )

        OutlinedTextField(
            value = if (value.isEmpty()) remain_value else value, // Use remain_value if value is empty
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = focusedColor,
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = focusedColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        val coverWidth = when (label.length) {
            in 1..5 -> 55.dp
            in 6..8 -> 75.dp
            else -> 110.dp
        }

        Box(
            modifier = Modifier
                .width(coverWidth)
                .height(6.dp)
                .background(Color.White)
                .offset(x = 20.dp, y = 7.dp)
        )
    }
    Spacer(modifier = Modifier.height(40.dp))
}
@Composable
fun ViewUserDetails(avatarID:Int,
                    userFullName:String,
                    rating:String,
                    position:String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), // Thêm padding để căn chỉnh
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically// Căn trái để mũi tên ở góc trái
    ) {
        Image(
            painter = painterResource(id = avatarID), // Ensure this is a valid drawable resource
            contentDescription = "Profile Avatar",
            modifier = Modifier
                .size(150.dp)
        )
        Column {
            Text(
                text = userFullName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Active member",
                fontSize = 18.sp,
                color = Color(0xFF7CCFA7)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Rating: $rating/5",
                fontSize = 18.sp,
                color = Color(0xFFBEB204),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(){
                Icon(
                    painter = painterResource(id = R.drawable.profile_view_location), // Icon for navigation
                    contentDescription = "Arrow Icon",
                    modifier = Modifier.size(20.dp),
                    tint=Color(0xFF544C44)
                )
                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = position,
                    fontSize = 18.sp,
                    color = Color(0xFF544C44)
                )
            }
        }
    }
}
@Composable
fun ViewRideDetails(ridesTaken:String,
                    ridesGiven:String,
                    trustScore:String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp), // Thêm padding để căn chỉnh
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically// Căn trái để mũi tên ở góc trái
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = ridesTaken,
                fontSize = 24.sp,
                color = Color(0xFF242760),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Rides taken",
                fontSize = 18.sp,
                color = Color(0xFF544C44),
                textAlign = TextAlign.Center
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = ridesGiven,
                fontSize = 24.sp,
                color = Color(0xFF242760),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Rides given",
                fontSize = 18.sp,
                color = Color(0xFF544C44),
                textAlign = TextAlign.Center
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = trustScore,
                fontSize = 24.sp,
                color = Color(0xFF242760),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Trust Score",
                fontSize = 18.sp,
                color = Color(0xFF544C44),
                textAlign = TextAlign.Center
            )
        }

    }
}
@Composable
fun RecentAccompany(
    avatarResId: Int,
    passengerName: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal=16.dp) // Đảm bảo column chiếm hết chiều rộng
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Căn chỉnh và thêm khoảng cách giữa các dòng
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Avatar
            Image(
                painter = painterResource(id = avatarResId), // Avatar người dùng
                contentDescription = "Passenger Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(25.dp)) // Bo tròn ảnh avatar
            )
            Spacer(modifier = Modifier.width(50.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Go with",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = passengerName,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        // Đường đứt nét ở cuối
        Divider(
            color = Color(0xFF8FC79A),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth() // Đảm bảo đường đứt nét dài bằng container
                .padding(top = 8.dp), // Khoảng cách giữa phần trên và đường đứt nét
        )
    }
}
@Composable
fun SavedLocation(iconID:Int, name: String,details:String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconID), // Icon for navigation
                contentDescription = "Arrow Icon",
                modifier = Modifier.size(30.dp),
                tint=Color.Gray
            )
            Spacer(modifier=Modifier.width((20.dp)))
            Column {
                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = details,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color=Color.Gray
                )
            }
        }
        Spacer(modifier=Modifier.height(5.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}
@Composable
fun KeCoinsDisplay(keCoins:String){
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(8.dp))
        .background(Color(0xFFFFC800))
    ){
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.Start){
            Text(
                text="Ké coins",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Text(
                text=keCoins,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = painterResource(id = R.drawable.coins_icon), // Ensure this is a valid drawable resource
                contentDescription = "Profile Avatar",
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}
@Composable
fun RedeemCodeInputhBar(value:String,onValueChange:(String)->Unit) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .padding(horizontal = 16.dp)
            .height(50.dp) // Tăng nhẹ chiều cao để có thêm không gian hiển thị
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFEFF8F2)),
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp // Đảm bảo dòng chữ không bị cắt
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFEFF8F2),
            unfocusedContainerColor = Color(0xFFEFF8F2),
            disabledContainerColor = Color(0xFFEFF8F2),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}
@Composable
fun RideCircleDetails(avatarID: Int,
                      trustScore: String,
                      ridesTaken: String,
                      ridesGiven: String){
    Row(modifier=Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)){
        Text(
            text = "Your trust score",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
    Spacer(modifier = Modifier.height(40.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), // Thêm padding để căn chỉnh
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically// Căn trái để mũi tên ở góc trái
    ) {
        Image(
            painter = painterResource(id = avatarID), // Ensure this is a valid drawable resource
            contentDescription = "Profile Avatar",
            modifier = Modifier
                .size(100.dp)
        )
        Spacer(modifier=Modifier.width(20.dp))
        Column(modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFDCF8EA))
            .border(
                1.dp,
                Color(0XFF7CCFA7),
                RoundedCornerShape(8.dp)

            )
            .padding(16.dp)
        ){
            Text(
                text = "Trust score: $trustScore",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0XFF7CCFA7)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Total Rides Taken: $ridesTaken",
                fontSize = 22.sp,
                color = Color(0xFF094DE0),
                fontWeight = FontWeight.Bold,

                )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Total Rides Given: $ridesGiven",
                fontSize = 22.sp,
                color = Color(0xFFF4B3B3),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
@Composable
fun favouriteRider(navController: NavController,userId:String,riderName:String,avatarID:Int,conversationId:String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = avatarID), // Ensure this is a valid drawable resource
                contentDescription = "Profile Avatar",
                modifier = Modifier
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                text = riderName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.chat_nav_icon), // Icon for navigation
                contentDescription = "Arrow Icon",
                modifier = Modifier.size(20.dp).clickable { navController.navigate("chat_details/$userId/$conversationId") },
            )
        }
        Spacer(modifier=Modifier.height(5.dp))
        Divider(color = Color(0XFF35B82A), thickness = 1.dp)
    }
}
@Composable
fun FAQSection(index:String, name: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = index,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier=Modifier.height(5.dp))
        Divider(color = Color(0xFF7CCFA7), thickness = 1.dp)
    }
}
@Composable
fun Media(iconID: Int, name: String, url: String) {
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)
        .clickable {
            // Open the URL when the composable is clicked
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconID), // Ensure this is a valid drawable resource
                contentDescription = "Profile Avatar",
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun Dev(avatarResId: Int, devName: String,devGmail:String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal=16.dp) // Đảm bảo column chiếm hết chiều rộng
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Căn chỉnh và thêm khoảng cách giữa các dòng
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Avatar
            Image(
                painter = painterResource(id = avatarResId), // Avatar người dùng
                contentDescription = "Passenger Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(25.dp)) // Bo tròn ảnh avatar
            )
            Spacer(modifier = Modifier.width(50.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = devName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color=Color(0xFF7CCFA7)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = devGmail,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        // Đường đứt nét ở cuối
        Divider(
            color = Color(0xFF8FC79A),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth() // Đảm bảo đường đứt nét dài bằng container
                .padding(top = 8.dp), // Khoảng cách giữa phần trên và đường đứt nét
        )
    }
}
@Composable
fun FAQContacUS(navController: NavController,userId: String,state:String){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Button(
            onClick = {if(state=="contact") navController.popBackStack() else {} },
            modifier = Modifier
                .width(180.dp)
                .height(30.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(if(state=="faq")0xFF8FC79A else 0xFFDCF8EA)),
            contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
        ) {
            Text(
                text = "FAQ",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
            )
        }

        Button(
            onClick = {if(state=="faq") navController.navigate("contact_us/$userId") else {} },
            modifier = Modifier
                .width(180.dp)
                .height(30.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(if(state=="contact")0xFF8FC79A else 0xFFDCF8EA)),
            contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
        ) {
            Text(
                text = "Contact Us",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
            )
        }
    }
}
@Composable
fun ShimmerProfileScreen(navController: NavController, userId: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header shimmer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                .background(Color(0xFF8FC79A))
        ) {
            // Nội dung shimmer bên trong header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterStart)
                    .shimmer(), // 💫 shimmer chỉ áp dụng lên phần bên trong
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Box cho text "My Profile"
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(160.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.4f))
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Box giả cho icon user
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.4f))
                )
            }
        }


        // Avatar + name + email block
        Column(
            modifier = Modifier
                .offset(y = -70.dp)
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .shimmer()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(120.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .width(180.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray)
                    )
                }
            }
        }


        // Shimmer cho các option
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .shimmer()
        ) {
            repeat(5) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth(0.8f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar(navController = navController, userId = userId, activate = 4)
    }
}


@Composable
fun ShimmerSavedLocationScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 👉 Header giả lập
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(24.dp)
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                    .shimmer()

            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 👉 Danh sách địa điểm shimmer
        repeat(3) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.LightGray, RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Box(
                        modifier = Modifier
                            .width(160.dp)
                            .height(18.dp)
                            .background(Color.LightGray, RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .width(220.dp)
                            .height(14.dp)
                            .background(Color.LightGray, RoundedCornerShape(4.dp))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 👉 Button shimmer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray, RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
fun ShimmerPromotionRewardsScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header shimmer
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(24.dp)
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                    .shimmer()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Avatar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // KeCoinsDisplay shimmer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(24.dp)
                    .background(Color.White, RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(30.dp)
                        .background(Color.White, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Redeem code section shimmer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.White, RoundedCornerShape(6.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(28.dp)
                        .background(Color.White, RoundedCornerShape(4.dp))
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                        .background(Color.White, RoundedCornerShape(25.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(50.dp)
                        .background(Color.White, RoundedCornerShape(25.dp))
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ShimmerRideCircleScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header shimmer
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(24.dp)
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                    .shimmer()

            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // RideCircleDetails shimmer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray, CircleShape)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(16.dp)
                        .background(Color.LightGray, RoundedCornerShape(6.dp))
                )
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(16.dp)
                        .background(Color.LightGray, RoundedCornerShape(6.dp))
                )
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(16.dp)
                        .background(Color.LightGray, RoundedCornerShape(6.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Title "Your favourite rider"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(24.dp)
                    .background(Color.LightGray, RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable shimmer danh sách riders
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .shimmer()
        ) {
            repeat(6) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.LightGray, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(18.dp)
                            .background(Color.LightGray, RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.LightGray, CircleShape)
                    )
                }
                Divider(color = Color(0XFF35B82A), thickness = 1.dp)
            }
        }
    }
}

@Composable
fun ShimmerProfileViewScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header shimmer
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(24.dp)
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                    .shimmer()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Avatar + User Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray, CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(20.dp)
                        .background(Color.LightGray, RoundedCornerShape(6.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(16.dp)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(16.dp)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Ride stats
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(3) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(20.dp)
                            .background(Color.LightGray, RoundedCornerShape(6.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(16.dp)
                            .background(Color.LightGray, RoundedCornerShape(4.dp))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Recent activity title
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(20.dp)
                    .background(Color.LightGray, RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Danh sách RecentAccompany shimmer
        repeat(4) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.LightGray, CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(20.dp)
                        .background(Color.LightGray, RoundedCornerShape(6.dp))
                )
            }
            Divider(color = Color(0xFF35B82A), thickness = 1.dp)
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun ShimmerEditProfileScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header shimmer
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(24.dp)
                    .background(Color.LightGray, RoundedCornerShape(6.dp))
                    .shimmer()

            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Avatar shimmer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Text fields shimmer
        repeat(4) {
            Column(modifier = Modifier.padding(bottom = 24.dp)) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(16.dp)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.LightGray, RoundedCornerShape(8.dp))
                )
            }
        }

        // Button shimmer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray, RoundedCornerShape(25.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
