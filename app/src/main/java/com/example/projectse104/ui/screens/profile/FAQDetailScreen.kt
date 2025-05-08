package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectse104.Component.*
import com.example.projectse104.ui.screens.profile.Component.*
@Composable
fun FAQDetailScreen(navController: NavController, categoryId: String) {
    val questions = when (categoryId) {
        "1" -> listOf(
            "How to create an account?" to "Go to the sign-up page and enter your email, password, and phone number.",
            "Can I change my email later?" to "Yes, go to settings > account > email and update your information.",
            "What happens if I forget my password?" to "Click 'Forgot Password' on the login screen and follow the instructions.",
            "How to delete my account?" to "Please contact support to request account deletion.",
            "How to change my phone number?" to "Go to your profile > settings > phone number to update it."
        )
        "2" -> listOf(
            "What payment methods are accepted?" to "We accept credit/debit cards, e-wallets, and bank transfers.",
            "Can I use promo codes?" to "Yes, enter the promo code at checkout.",
            "How to check my payment history?" to "Go to Profile > Payments > History.",
            "Why was my payment declined?" to "Check with your bank or try another payment method.",
            "How do I get a refund?" to "Contact our support within 24 hours of the transaction."
        )
        "3" -> listOf(
            "How to rate a driver?" to "After each trip, you'll be prompted to leave a rating.",
            "Can I block a driver or passenger?" to "Yes, report them and we'll handle it accordingly.",
            "How are drivers verified?" to "Drivers must submit valid documents and undergo training.",
            "What if my driver is late?" to "You can cancel after 5 minutes without charge.",
            "Can passengers select drivers?" to "Driver assignment is based on proximity and rating."
        )
        "4" -> listOf(
            "What safety measures are in place?" to "We verify identities and offer 24/7 support.",
            "How do I contact support?" to "Use the Help section or call our hotline directly.",
            "What to do in an emergency?" to "Use the SOS button or contact local authorities.",
            "Is my personal data protected?" to "Yes, we comply with all data protection regulations.",
            "How to report an incident?" to "Go to Profile > Support > Report an issue."
        )
        else -> emptyList()
    }

    val title = when (categoryId) {
        "1" -> "General Questions"
        "2" -> "Payments & Promotions"
        "3" -> "Driver & Passengers"
        "4" -> "Safety & Support"
        else -> "FAQs"
    }
Row(modifier = Modifier.fillMaxSize()
                .background(Color.White)) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BackArrowWithText(navController, title)

        Spacer(modifier = Modifier.height(16.dp))

        questions.forEachIndexed { index, (question, answer) ->
            ExpandableFAQItem(
                index = (index + 1).toString(),
                question = question,
                answer = answer
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
}