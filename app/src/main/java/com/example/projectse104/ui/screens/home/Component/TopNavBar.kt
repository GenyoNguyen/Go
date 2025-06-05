package com.example.projectse104.ui.screens.home.Component

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
import com.example.projectse104.Component.*
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
        Spacer(modifier = Modifier.width(12.dp))
        Button(
            onClick = {if (index==2) {} else{navController.navigate("find_a_ride/$userId")} },
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if(index==2) Color(0xFFF3F8FE) else Color.White)
        ) {
            Text(text = "Find a Ride", color = Color(if(index==2) 0xFF186FF0 else 0xFFBFBFBF))
        }
        Spacer(modifier = Modifier.width(12.dp))
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