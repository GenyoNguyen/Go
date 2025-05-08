package com.example.projectse104.ui.screens.auth.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.projectse104.*
import com.example.projectse104.Component.*
@Composable
fun SocialMedia(){
    Box(modifier=Modifier.fillMaxWidth()){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.facebook), contentDescription = "Facebook", modifier = Modifier.size(40.dp).clickable {})
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.twitter), contentDescription = "Twitter", modifier = Modifier.size(40.dp).clickable {})
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.google), contentDescription = "Google", modifier = Modifier.size(40.dp).clickable {})
        }
    }
}