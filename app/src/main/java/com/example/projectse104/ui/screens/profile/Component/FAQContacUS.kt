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
import com.example.projectse104.Component.*
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.rememberShimmer
@Composable
fun FAQContacUS(navController: NavController,userId: String,state:String){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Button(
            onClick = {if(state=="contact") navController.popBackStack() else {} },
            modifier = Modifier
                .width(150.dp)
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
                .width(150.dp)
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