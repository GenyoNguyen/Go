package com.example.projectse104

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

@Composable
fun CustomTextFieldWithLabel(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusedColor = Color(0xFF8FC79A)

    Box(modifier = Modifier.fillMaxWidth()) {
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
            value = value,
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
            else -> 90.dp
        }

        Box(
            modifier = Modifier
                .width(coverWidth)
                .height(6.dp)
                .background(Color.White)
                .offset(x = 20.dp, y = 7.dp)
        )
    }
}
@Composable
fun CustomPasswordTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusedColor = Color(0xFF8FC79A)
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
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
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = focusedColor
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = focusedColor,
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = focusedColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        val coverWidth = when (label) {
            "EMAIL"->100.dp
            "Password"->80.dp
            "New password" -> 100.dp
            "Confirm password"->120.dp
            "Confirm new password"->140.dp
            else ->10.dp
        }
        Box(
            modifier = Modifier
                .width(coverWidth)
                .height(6.dp)
                .background(Color.White)
                .offset(x = 20.dp, y = 7.dp)
        )
    }
}
@Composable
fun BigButton(navController: NavController,
              text:String,
              onClick: () -> Unit = {}){
    Box(modifier=Modifier.fillMaxWidth()){
        Button(
            onClick = { onClick()},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A))
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun BackArrowWithText(navController: NavController,text:String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp), // Thêm padding để căn chỉnh
            verticalAlignment = Alignment.CenterVertically, // Căn giữa theo chiều dọc
            horizontalArrangement = Arrangement.Start // Căn trái để mũi tên ở góc trái
        ) {
            // Mũi tên quay lại
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tiêu đề "Forgot password"
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Căn giữa theo chiều ngang
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .offset(x = -15.dp) // Căn giữa hoàn toàn
            )
        }
    }
@Composable
fun BottomNavigationBar(navController: NavController, userId: String, activate: Int) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White // Nền trắng, không nổi bật
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (activate == 1) R.drawable.home_icon_active else R.drawable.home_icon
                    ),
                    contentDescription = "Home",
                    modifier = Modifier.size(100.dp),
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
                    modifier = Modifier.size(100.dp),
                    tint = Color.Unspecified
                )
            },
            selected = activate == 2,
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.Unspecified,
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
                    modifier = Modifier.size(100.dp),
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
                    modifier = Modifier.size(100.dp),
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
@Composable
fun RideItem(
    navController: NavController,
    rideNo: String="",
    estimatedDeparture: String="",
    fromLocation: String="",
    toLocation: String="",
    avatarResId: Int, // Thêm tham số avatarResId để truyền ảnh
    route: String="", // Thêm tham số route
    userId:String="",
    passengerFullName:String="",
    passengerID:String="",
    riderName:String="",
    riderId:String="",
    coinsEarned:String="",
    addGoButton:String=""
) {
    var path:String=if (route=="ride_details") "$route/$userId/$rideNo/$estimatedDeparture/$fromLocation/$toLocation/$riderName/$riderId/$passengerFullName/$passengerID/$coinsEarned/$addGoButton"
    else if (route=="ride_details_history") "$route/$userId/$rideNo/$estimatedDeparture/$fromLocation/$toLocation/$riderName/$riderId/$passengerFullName/$passengerID/$coinsEarned"
    else "$route/$userId/$rideNo/$estimatedDeparture/$fromLocation/$toLocation/$riderName/$riderId/$coinsEarned"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp) // Độ dày của top border
                .background(Color(0xFF8FC79A)) // Màu của top border
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically, // Căn giữa ảnh theo chiều dọc trong Row
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Ride No. $rideNo", // Sử dụng tham số rideNo
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Căn giữa ảnh theo chiều dọc trong Row
                ) {
                    Text(
                        text = "Estimated Departure",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = estimatedDeparture, // Sử dụng tham số estimatedDeparture
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Căn giữa ảnh theo chiều dọc trong Row
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.path), // Đổi lại với icon của bạn
                        contentDescription = "Path Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "From: $fromLocation - To: $toLocation", // Sử dụng tham số từ and đến
                        color = Color(0xFF8FC79A),
                        fontSize = 15.sp
                    )
                }
            }
            Image(
                painter = painterResource(id = avatarResId), // Sử dụng tham số avatarResId
                contentDescription = "Avatar",
                modifier = Modifier
                    .height(75.dp) // Chiều cao bằng 80% container của nó (phần tử cha)
                    .aspectRatio(1f) // Đảm bảo tỉ lệ 1:1 cho ảnh (vì bạn chỉ định chiều cao)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navController.navigate(path)
            },
            modifier = Modifier
                .width(100.dp)
                .height(30.dp)
                .align(Alignment.End), // Căn chỉnh button về bên phải
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
            contentPadding = PaddingValues(0.dp) // Loại bỏ padding nội dung để text không bị cắt
        ) {
            Text(
                text = "Details",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}
@Composable
fun Header(text:String,iconID:Int){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(8.dp)) // Bo tròn 4 góc của header
            .background(Color(0xFF8FC79A)), // Thêm background sau khi clip
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Row cho Home và Hi, {userId}
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start, // Tạo khoảng cách giữa "Home" và "Hi, {userId}"
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thêm icon ngôi nhà cạnh chữ Home
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và chữ "Home"
                Image(
                    painter = painterResource(id = R.drawable.chat_icon_header), // Đổi lại với icon của bạn
                    contentDescription = "Home Icon",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}