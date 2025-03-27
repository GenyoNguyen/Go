package com.example.projectse104.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projectse104.ui.screens.auth.LoginScreen
import com.example.projectse104.ui.screens.auth.SignupScreen
import com.example.projectse104.ui.screens.home.HomeScreen
import com.example.projectse104.ui.screens.chat.ChatScreen
import com.example.projectse104.ui.screens.chat.ChatDetailsScreen
import com.example.projectse104.ui.screens.profile.ProfileScreen
import com.example.projectse104.ui.screens.profile.ProfileViewScreen
import com.example.projectse104.ui.screens.profile.EditProfileScreen
import com.example.projectse104.ui.screens.profile.RideCircleScreen
import com.example.projectse104.ui.screens.profile.PromotionRewardsScreen
import com.example.projectse104.ui.screens.profile.SavedLocationScreen
import com.example.projectse104.ui.screens.profile.HelpSupportScreen
import com.example.projectse104.ui.screens.profile.ContactUsScreen
import com.example.projectse104.ui.screens.history.HistoryScreen
import com.example.projectse104.ui.screens.history.RideDetailsHistoryScreen
import com.example.projectse104.ui.screens.history.RideDetailsRatingScreen
import com.example.projectse104.ui.screens.home.FindARideScreen
import com.example.projectse104.ui.screens.home.RideDetailsScreen
import com.example.projectse104.ui.screens.home.ConfirmRideScreen
import com.example.projectse104.ui.screens.home.BookingSuccessfulScreen
import com.example.projectse104.ui.screens.home.OfferARideScreen
import com.example.projectse104.ui.screens.home.OfferDetailsScreen
import com.example.projectse104.ui.screens.home.ConfirmRequestScreen
import com.example.projectse104.ui.screens.home.AddNewOfferScreen1
import com.example.projectse104.ui.screens.home.AddNewOfferScreen2
import com.example.projectse104.ui.screens.home.AddNewOfferScreen3
import com.example.projectse104.ui.screens.home.AddNewOfferScreen4
import com.example.projectse104.ui.screens.home.AddNewOfferSuccessfullyScreen
import com.example.projectse104.ui.screens.welcome.WelcomeScreen
import com.example.projectse104.ui.screens.onboarding.OnBoardingScreen1
import com.example.projectse104.ui.screens.onboarding.OnBoardingScreen2
import com.example.projectse104.ui.screens.onboarding.OnBoardingScreen3
import com.example.projectse104.ui.screens.auth.SignUpAndSignInScreen
import com.example.projectse104.ui.screens.auth.ForgotPasswordScreen
import com.example.projectse104.ui.screens.auth.VerifyEmailScreen
import com.example.projectse104.ui.screens.auth.NewPasswordScreen
import com.example.projectse104.ui.screens.auth.ResetPasswordSuccessfulScreen
import com.example.projectse104.ui.screens.auth.LoginSuccessfulScreen

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
            Screen.Home.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            HomeScreen(navController = navController,userId = userId)
        }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }
        composable(Screen.SignUpAndSignIn.route) { SignUpAndSignInScreen(navController) }
        composable(Screen.VerifyEmail.route) { VerifyEmailScreen(navController) }
        composable(Screen.NewPassword.route) { NewPasswordScreen(navController) }
        composable(Screen.ResetPasswordSuccessful.route) { ResetPasswordSuccessfulScreen(navController) }
        composable(Screen.LoginSuccessful.route) { LoginSuccessfulScreen(navController) }
        composable(
            Screen.Chat.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            ChatScreen(navController = navController,userId = userId)
        }
        composable(
            Screen.History.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            HistoryScreen(navController = navController,userId = userId)
        }
        composable(
            Screen.Profile.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            // Truyền tham số vào ConfirmRideScreen
            ProfileScreen(navController = navController,userId = userId)
        }
        composable(
            Screen.FindARide.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            // Truyền tham số vào ConfirmRideScreen
            FindARideScreen(navController = navController,userId = userId)
        }
        composable(
            Screen.RideDetails.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType },
                navArgument("addGoButton") { type = NavType.StringType }

            )
        ) { backStackEntry ->
            // Lấy các tham số từ backStackEntry
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""
            val addGoButton = backStackEntry.arguments?.getString("addGoButton") ?: ""

            // Truyền tham số vào RideDetailsScreen
            RideDetailsScreen(
                navController = navController,
                userId=userId,
                rideNo = rideNo,
                addGoButton=addGoButton
            )
        }
        composable(
            Screen.RideDetailsHistory.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            // Lấy các tham số từ backStackEntry
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""

            // Truyền tham số vào RideDetailsScreen
            RideDetailsHistoryScreen(
                navController = navController,
                userId=userId,
                rideNo = rideNo,
            )
        }
        composable(
            Screen.ConfirmRide.route,
            arguments = listOf(
                navArgument("riderName") { type = NavType.StringType },
                navArgument("rideID") { type = NavType.StringType },
                navArgument("userId") { type = NavType.StringType },
                )
        ) { backStackEntry ->
            // Lấy tham số từ backStackEntry
            val riderName = backStackEntry.arguments?.getString("riderName") ?: ""
            val rideID = backStackEntry.arguments?.getString("rideID") ?: ""
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            // Truyền tham số vào ConfirmRideScreen
            ConfirmRideScreen(navController = navController,riderName=riderName,rideID=rideID, userId = userId)
        }

        composable(
            Screen.BookingSuccessful.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userId= backStackEntry.arguments?.getString("userId") ?: ""

            BookingSuccessfulScreen(navController = navController,userId=userId)
        }

        composable(
            Screen.OfferARide.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            // Truyền tham số vào ConfirmRideScreen
            OfferARideScreen(navController = navController,userId = userId)
        }
        composable(
            Screen.OfferDetails.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            // Lấy các tham số từ backStackEntry
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""

            // Truyền tham số vào RideDetailsScreen
            OfferDetailsScreen(
                navController = navController,
                userId=userId,
                rideNo = rideNo,
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
            // Lấy tham số từ backStackEntry
            val passengerName = backStackEntry.arguments?.getString("passengerName") ?: ""
            val rideID = backStackEntry.arguments?.getString("rideID") ?: ""
            val riderName = backStackEntry.arguments?.getString("riderName") ?: ""

            // Truyền tham số vào ConfirmRequestScreen
            ConfirmRequestScreen(
                navController = navController,
                passengerName = passengerName,
                rideID = rideID,
                riderName = riderName,
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
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AddNewOfferScreen2(navController, userId)
        }
        composable(
            Screen.AddNewOffer3.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AddNewOfferScreen3(navController, userId)
        }
        composable(
            Screen.AddNewOffer4.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AddNewOfferScreen4(navController, userId)
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
                navArgument("conversationId") { type = NavType.StringType },)
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val conversationId = backStackEntry.arguments?.getString("conversationId") ?: ""
            ChatDetailsScreen(navController, userId,conversationId)
        }
        composable(
            Screen.RideDetailsRating.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType },)
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""

            RideDetailsRatingScreen(navController, userId,rideNo)
        }
        composable(
            Screen.ProfileView.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ProfileViewScreen(navController, userId)
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
    }

}
