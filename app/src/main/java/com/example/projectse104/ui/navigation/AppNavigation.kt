package com.example.projectse104.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projectse104.ui.screens.auth.ForgotPasswordScreen
import com.example.projectse104.ui.screens.auth.LoginScreen
import com.example.projectse104.ui.screens.auth.LoginSuccessfulScreen
import com.example.projectse104.ui.screens.auth.NewPasswordScreen
import com.example.projectse104.ui.screens.auth.ResetPasswordSuccessfulScreen
import com.example.projectse104.ui.screens.auth.SignUpAndSignInScreen
import com.example.projectse104.ui.screens.auth.SignupScreen
import com.example.projectse104.ui.screens.auth.VerifyEmailScreen
import com.example.projectse104.ui.screens.chat.ChatDetailsScreen
import com.example.projectse104.ui.screens.chat.ChatScreen
import com.example.projectse104.ui.screens.history.HistoryScreen
import com.example.projectse104.ui.screens.history.RideDetailsHistoryScreen
import com.example.projectse104.ui.screens.history.RideDetailsRatingScreen
import com.example.projectse104.ui.screens.home.AddNewOfferScreen1
import com.example.projectse104.ui.screens.home.AddNewOfferScreen2
import com.example.projectse104.ui.screens.home.AddNewOfferScreen3
import com.example.projectse104.ui.screens.home.AddNewOfferScreen4
import com.example.projectse104.ui.screens.home.AddNewOfferSuccessfullyScreen
import com.example.projectse104.ui.screens.home.BookingSuccessfulScreen
import com.example.projectse104.ui.screens.home.ConfirmRequestScreen
import com.example.projectse104.ui.screens.home.ConfirmRideScreen
import com.example.projectse104.ui.screens.home.FindARideScreen
import com.example.projectse104.ui.screens.home.HomeScreen
import com.example.projectse104.ui.screens.home.OfferARideScreen
import com.example.projectse104.ui.screens.home.OfferDetailsScreen
import com.example.projectse104.ui.screens.home.RideDetailsScreen
import com.example.projectse104.ui.screens.onboarding.OnBoardingScreen1
import com.example.projectse104.ui.screens.onboarding.OnBoardingScreen2
import com.example.projectse104.ui.screens.onboarding.OnBoardingScreen3
import com.example.projectse104.ui.screens.profile.AddNewAddressScreen
import com.example.projectse104.ui.screens.profile.ContactUsScreen
import com.example.projectse104.ui.screens.profile.EditProfileScreen
import com.example.projectse104.ui.screens.profile.FAQDetailScreen
import com.example.projectse104.ui.screens.profile.HelpSupportScreen
import com.example.projectse104.ui.screens.profile.ProfileScreen
import com.example.projectse104.ui.screens.profile.ProfileViewScreen
import com.example.projectse104.ui.screens.profile.PromotionRewardsScreen
import com.example.projectse104.ui.screens.profile.RideCircleScreen
import com.example.projectse104.ui.screens.profile.SavedLocationScreen
import com.example.projectse104.ui.screens.welcome.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.OnBoarding1.route) { OnBoardingScreen1(navController) }
        composable(Screen.OnBoarding2.route) { OnBoardingScreen2(navController) }
        composable(Screen.OnBoarding3.route) { OnBoardingScreen3(navController) }
        composable(Screen.SignIn.route) { LoginScreen(navController) }
        composable(Screen.SignUp.route) { SignupScreen(navController) }
        composable(
            route = "login_successful/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            LoginSuccessfulScreen(navController = navController, userId = userId)
        }
        composable(
            Screen.Home.route, // "home/{userId}"
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            HomeScreen(navController = navController, userId = userId)
        }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }
        composable(Screen.SignUpAndSignIn.route) { SignUpAndSignInScreen(navController) }
        composable(Screen.VerifyEmail.route) { VerifyEmailScreen(navController) }
        composable(Screen.NewPassword.route) { NewPasswordScreen(navController) }
        composable(Screen.ResetPasswordSuccessful.route) {
            ResetPasswordSuccessfulScreen(navController)
        }
        composable(
            Screen.Chat.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ChatScreen(navController = navController, userId = userId)
        }
        composable(
            Screen.History.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            HistoryScreen(navController = navController, userId = userId)
        }
        composable(
            Screen.Profile.route, // "profile/{userId}"
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ProfileScreen(navController = navController, userId = userId)
        }
        composable(
            Screen.FindARide.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            FindARideScreen(navController = navController, userId = userId)
        }
        composable(
            Screen.RideDetails.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType },
                navArgument("addGoButton") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""
            val addGoButton = backStackEntry.arguments?.getString("addGoButton") ?: ""
            RideDetailsScreen(
                navController = navController,
                userId = userId,
                rideNo = rideNo,
                addGoButton = addGoButton
            )
        }
        composable(
            Screen.RideDetailsHistory.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""
            RideDetailsHistoryScreen(
                navController = navController,
                userId = userId,
                rideId = rideNo
            )
        }
        composable(
            Screen.ConfirmRide.route,
            arguments = listOf(
                navArgument("riderName") { type = NavType.StringType },
                navArgument("rideID") { type = NavType.StringType },
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val riderName = backStackEntry.arguments?.getString("riderName") ?: ""
            val rideID = backStackEntry.arguments?.getString("rideID") ?: ""
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ConfirmRideScreen(
                navController = navController,
                riderName = riderName,
                rideID = rideID,
                userId = userId
            )
        }
        composable(
            Screen.BookingSuccessful.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            BookingSuccessfulScreen(navController = navController, userId = userId)
        }
        composable(
            Screen.OfferARide.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            OfferARideScreen(navController = navController, userId = userId)
        }
        composable(
            Screen.OfferDetails.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""
            OfferDetailsScreen(
                navController = navController,
                userId = userId,
                rideNo = rideNo
            )
        }
        composable(
            Screen.ConfirmRequest.route,
            arguments = listOf(
                navArgument("passengerName") { type = NavType.StringType },
                navArgument("rideID") { type = NavType.StringType },
                navArgument("riderName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val passengerName = backStackEntry.arguments?.getString("passengerName") ?: ""
            val rideID = backStackEntry.arguments?.getString("rideID") ?: ""
            val riderName = backStackEntry.arguments?.getString("riderName") ?: ""
            ConfirmRequestScreen(
                navController = navController,
                passengerName = passengerName,
                rideID = rideID,
                riderName = riderName
            )
        }
        composable(
            Screen.AddNewOffer1.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AddNewOfferScreen1(navController, userId)
        }
        composable(
            Screen.AddNewOffer2.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val time = backStackEntry.arguments?.getString("time") ?: ""
            AddNewOfferScreen2(navController, userId,time)
        }
        composable(
            Screen.AddNewOffer3.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType },
                navArgument("departureLocationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val time = backStackEntry.arguments?.getString("time") ?: ""
            val departureLocationId = backStackEntry.arguments?.getString("departureLocationId") ?: ""

            AddNewOfferScreen3(navController, userId,time,departureLocationId)
        }
        composable(
            Screen.AddNewOffer4.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType },
                navArgument("departureLocationId") { type = NavType.StringType },
                navArgument("toLocationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val time = backStackEntry.arguments?.getString("time") ?: ""
            val departureLocationId = backStackEntry.arguments?.getString("departureLocationId") ?: ""
            val toLocationId = backStackEntry.arguments?.getString("toLocationId") ?: ""
            AddNewOfferScreen4(navController, userId, time, departureLocationId, toLocationId)
        }
        composable(
            Screen.AddNewOfferSuccessfully.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AddNewOfferSuccessfullyScreen(navController, userId)
        }
        composable(
            Screen.ChatDetails.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("conversationId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val conversationId = backStackEntry.arguments?.getString("conversationId") ?: ""
            ChatDetailsScreen(navController, userId, conversationId)
        }
        composable(
            Screen.RideDetailsRating.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""
            RideDetailsRatingScreen(navController, userId, rideNo)
        }
        composable(
            Screen.ProfileView.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("hideNav") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val hideNav = backStackEntry.arguments?.getString("hideNav") ?: ""
            ProfileViewScreen(navController, userId, hideNav)
        }
        composable(
            Screen.EditProfile.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            EditProfileScreen(navController, userId)
        }
        composable(
            Screen.RideCircle.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            RideCircleScreen(navController, userId)
        }
        composable(
            Screen.PromotionRewards.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            PromotionRewardsScreen(navController, userId)
        }
        composable(
            Screen.SavedLocations.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            SavedLocationScreen(navController, userId)
        }
        composable(
            Screen.HelpSupport.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            HelpSupportScreen(navController, userId)
        }
        composable(
            Screen.ContactUs.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ContactUsScreen(navController, userId)
        }
        composable(
            Screen.AddNewAddress.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AddNewAddressScreen(navController, userId)
        }
        composable(
            Screen.FAQDetail.route,
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index") ?: ""
            FAQDetailScreen(navController, index)
        }
    }
}