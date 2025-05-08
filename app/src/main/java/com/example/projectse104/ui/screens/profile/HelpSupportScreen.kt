package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectse104.Component.*
import com.example.projectse104.ui.screens.profile.Component.*

@Composable
fun HelpSupportScreen(navController: NavController, userId: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        BackArrowWithText(navController,"Help & Support")

        Spacer(modifier=Modifier.height(20.dp).padding(horizontal=16.dp))

        FAQContacUS(navController,userId,"faq")

        Spacer(modifier=Modifier.height(50.dp))
        FAQSection("1", "General Question") {
            navController.navigate("faq_detail/1")
        }
        FAQSection("2", "Payments & Promotions") {
            navController.navigate("faq_detail/2")
        }
        FAQSection("3", "Driver & Passengers") {
            navController.navigate("faq_detail/3")
        }
        FAQSection("4", "Safety & Support") {
            navController.navigate("faq_detail/4")
        }

        Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
        BottomNavigationBar(navController, userId, 4)
    }
}
