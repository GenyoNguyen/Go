package com.example.projectse104.ui.screens.home

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import androidx.compose.material3.Card
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.example.projectse104.*
import com.valentinilk.shimmer.shimmer
import java.util.Calendar

@Composable
fun TopNavBar(navController: NavController,userId:String,index:Int){
    Row(
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = {if (index==1) {} else {navController.navigate("home/$userId")}},
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if(index==1) Color(0xFFF3F8FE) else Color.White)
        ) {
            Text(text = "Rides", color = Color(if(index==1) 0xFF186FF0 else 0xFFBFBFBF))
        }
        Button(
            onClick = {if (index==2) {} else{navController.navigate("find_a_ride/$userId")} },
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if(index==2) Color(0xFFF3F8FE) else Color.White)
        ) {
            Text(text = "Find a Ride", color = Color(if(index==2) 0xFF186FF0 else 0xFFBFBFBF))
        }
        Button(
            onClick = { if (index==3) {} else {navController.navigate("offer_a_ride/$userId")} },
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if(index==3) Color(0xFFF3F8FE) else Color.White)
        ) {
            Text(text = "Offer a Ride", color = Color(if(index==3) 0xFF186FF0 else 0xFFBFBFBF))
        }
    }
}
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) } // Quản lý trạng thái nhập văn bản

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = {
            Text(
                "Where do you want to go today?",
                fontSize = 16.sp,
                color = Color.Gray,
                lineHeight = 20.sp // Giúp văn bản không bị cắt
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp) // Tăng nhẹ chiều cao để có thêm không gian hiển thị
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFEFF8F2)),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        },
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
        singleLine = true // Giữ văn bản trên một dòng duy nhất
    )
}
@Composable
fun HomeHeader(userName:String){
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
            horizontalArrangement = Arrangement.SpaceBetween, // Tạo khoảng cách giữa "Home" và "Hi, {userId}"
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thêm icon ngôi nhà cạnh chữ Home
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Home",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và chữ "Home"
                Image(
                    painter = painterResource(id = R.drawable.header_home), // Đổi lại với icon của bạn
                    contentDescription = "Home Icon",
                    modifier = Modifier.size(30.dp)
                )
            }

            // Text "Hi, {userId}"
            Text(
                text = "Hi, $userName 👋", // Sử dụng tham số userId
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
@Composable
fun YesNoButtons(navController: NavController,
                 yesOnClick:()->Unit={},
                 noOnClick:()->Unit={}){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
    ) {
        Button(
            onClick = {
                noOnClick()
            },
            modifier = Modifier
                .width(150.dp)
                .height(40.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "NO",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                yesOnClick()
            },
            modifier = Modifier
                .width(150.dp)
                .height(40.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "YES!",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun PassengerItem(
    navController: NavController,
    avatarResId: Int,
    passengerName: String,
    onAcceptClick: () -> Unit // Hành động khi nhấn "Accept"
) {
    Column(
        modifier = Modifier.fillMaxWidth() // Đảm bảo column chiếm hết chiều rộng
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Căn chỉnh và thêm khoảng cách giữa các dòng
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Avatar
            Image(
                painter = painterResource(id = avatarResId), // Avatar người dùng
                contentDescription = "Passenger Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(25.dp)) // Bo tròn ảnh avatar
            )

            // Tên hành khách
            Text(
                text = passengerName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            // Nút Accept
            Button(
                onClick = { onAcceptClick() }, // Xử lý khi nhấn Accept
                modifier = Modifier
                    .width(100.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FC79A)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Accept",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically) // Căn giữa text theo chiều dọc
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
fun InputhBar(value:String,onValueChange:(String)->Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(0.8f)
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
fun TimePickerField(
    timeText: String,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFEFF8F2))
            .clickable { showTimePicker(context) { selectedTime -> onValueChange(selectedTime) } },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = timeText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (timeText == "Select Time") Color.Gray else Color.Black
        )
    }
}

// Hàm hiển thị `TimePickerDialog`
fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(context, { _, selectedHour, selectedMinute ->
        val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
        onTimeSelected(formattedTime)
    }, hour, minute, true).show()
}

@Composable
fun OfferDetails(estimatedDeparture: String,
                 fromLocation: String,
                 toLocation: String,
                 riderName:String,
                 riderUserId:String,
                 cost:String){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$estimatedDeparture",
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Route: From $fromLocation to $toLocation",
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Rider: ",
                fontSize = 15.sp
            )
            Text(
                text = "$riderName",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "- (UserID: $riderUserId)",
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Status: ",
                fontSize = 15.sp
            )
            Text(
                text = "Success",
                fontSize = 15.sp,
                color = Color.Green
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Passenger Information: ???",
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Cost: $cost ",
                fontSize = 15.sp,
            )
            Text(
                text = "Ké Coins",
                fontSize = 15.sp,
                color = Color.Yellow
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Potential Passengers Section
        // Potential Passengers Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Potential Passengers",
                fontSize = 23.sp,
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
    }
}
@Composable
fun AddNewOffer(navController: NavController,userId: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .clickable {
                navController.navigate("add_new_offer1/$userId") // Điều hướng đến màn hình tạo chuyến đi mới
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_offer_icon), // Ảnh dấu "+"
            contentDescription = "Add Offer Icon",
            modifier = Modifier.size(32.dp) // Kích thước icon
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Add new offer",
            fontSize = 18.sp,
            color = Color(0xFF8FC79A),
            fontWeight = FontWeight.Medium
        )
    }
}
@Composable
fun AddNewOfferContent(iconId:Int,text:String){
    Image(
        painter = painterResource(id = iconId), // Ảnh đồng hồ
        contentDescription = "Clock Icon",
        modifier = Modifier.size(200.dp)
    )

    Spacer(modifier = Modifier.height(40.dp))

    // Câu hỏi
    Text(
        text = text,
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black
    )
}
@Composable
fun ShimmerHomeScreen(navController: NavController, userId: String, index:Int,active:Int) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, userId, active)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            ShimmerHomeHeader()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopNavBar(navController, userId, index)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .shimmer(), // 💫 shimmer ở đây
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(6) { // giả lập 6 dòng shimmer
                    ShimmerRideItem()
                }
            }
        }
    }
}
@Composable
fun ShimmerHomeHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF8FC79A)) // cùng màu với HomeHeader gốc
            .shimmer(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.LightGray)
                )
            }

            Box(
                modifier = Modifier
                    .height(24.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}