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
                navArgument("userName") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""

            HomeScreen(navController = navController,userName = userName)
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
                navArgument("userName") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""

            ChatScreen(navController = navController,userName = userName)
        }
        composable(
            Screen.History.route,
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""

            HistoryScreen(navController = navController,userName = userName)
        }
        composable(
            Screen.Profile.route,
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""

            // Truyền tham số vào ConfirmRideScreen
            ProfileScreen(navController = navController,userName = userName)
        }
        composable(
            Screen.FindARide.route,
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""

            // Truyền tham số vào ConfirmRideScreen
            FindARideScreen(navController = navController,userName = userName)
        }
        composable(
            Screen.RideDetails.route,
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType },
                navArgument("estimatedDeparture") { type = NavType.StringType },
                navArgument("fromLocation") { type = NavType.StringType },
                navArgument("toLocation") { type = NavType.StringType },
                navArgument("riderName") { type = NavType.StringType },
                navArgument("riderUserId") { type = NavType.StringType },
                navArgument("passengerName") { type = NavType.StringType },
                navArgument("passengerUserId") { type = NavType.StringType },
                navArgument("cost") { type = NavType.StringType } ,
                navArgument("addGoButton") { type = NavType.StringType }

            )
        ) { backStackEntry ->
            // Lấy các tham số từ backStackEntry
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""
            val estimatedDeparture = backStackEntry.arguments?.getString("estimatedDeparture") ?: ""
            val fromLocation = backStackEntry.arguments?.getString("fromLocation") ?: ""
            val toLocation = backStackEntry.arguments?.getString("toLocation") ?: ""
            val riderName = backStackEntry.arguments?.getString("riderName") ?: ""
            val riderUserId = backStackEntry.arguments?.getString("riderUserId") ?: ""
            val passengerName = backStackEntry.arguments?.getString("passengerName") ?: ""
            val passengerUserId = backStackEntry.arguments?.getString("passengerUserId") ?: ""
            val cost = backStackEntry.arguments?.getString("cost") ?: ""
            val addGoButton = backStackEntry.arguments?.getString("addGoButton") ?: ""

            // Truyền tham số vào RideDetailsScreen
            RideDetailsScreen(
                navController = navController,
                userName=userName,
                rideNo = rideNo,
                estimatedDeparture = estimatedDeparture,
                fromLocation = fromLocation,
                toLocation = toLocation,
                riderName = riderName,
                riderUserId = riderUserId,
                passengerName = passengerName,
                passengerUserId = passengerUserId,
                cost = cost,
                addGoButton=addGoButton
            )
        }
        composable(
            Screen.RideDetailsHistory.route,
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType },
                navArgument("estimatedDeparture") { type = NavType.StringType },
                navArgument("fromLocation") { type = NavType.StringType },
                navArgument("toLocation") { type = NavType.StringType },
                navArgument("riderName") { type = NavType.StringType },
                navArgument("riderUserId") { type = NavType.StringType },
                navArgument("passengerName") { type = NavType.StringType },
                navArgument("passengerUserId") { type = NavType.StringType },
                navArgument("cost") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Lấy các tham số từ backStackEntry
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""
            val estimatedDeparture = backStackEntry.arguments?.getString("estimatedDeparture") ?: ""
            val fromLocation = backStackEntry.arguments?.getString("fromLocation") ?: ""
            val toLocation = backStackEntry.arguments?.getString("toLocation") ?: ""
            val riderName = backStackEntry.arguments?.getString("riderName") ?: ""
            val riderUserId = backStackEntry.arguments?.getString("riderUserId") ?: ""
            val passengerName = backStackEntry.arguments?.getString("passengerName") ?: ""
            val passengerUserId = backStackEntry.arguments?.getString("passengerUserId") ?: ""
            val cost = backStackEntry.arguments?.getString("cost") ?: ""

            // Truyền tham số vào RideDetailsScreen
            RideDetailsHistoryScreen(
                navController = navController,
                userName=userName,
                rideNo = rideNo,
                estimatedDeparture = estimatedDeparture,
                fromLocation = fromLocation,
                toLocation = toLocation,
                riderName = riderName,
                riderUserId = riderUserId,
                passengerName = passengerName,
                passengerUserId = passengerUserId,
                cost = cost
            )
        }
        composable(
            Screen.ConfirmRide.route,
            arguments = listOf(
                navArgument("riderName") { type = NavType.StringType },
                navArgument("rideID") { type = NavType.StringType },
                navArgument("userName") { type = NavType.StringType },
                )
        ) { backStackEntry ->
            // Lấy tham số từ backStackEntry
            val riderName = backStackEntry.arguments?.getString("riderName") ?: ""
            val rideID = backStackEntry.arguments?.getString("rideID") ?: ""
            val userName = backStackEntry.arguments?.getString("userName") ?: ""

            // Truyền tham số vào ConfirmRideScreen
            ConfirmRideScreen(navController = navController,riderName=riderName,rideID=rideID, userName = userName)
        }

        composable(
            Screen.BookingSuccessful.route,
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userName= backStackEntry.arguments?.getString("userName") ?: ""

            BookingSuccessfulScreen(navController = navController,userName=userName)
        }

        composable(
            Screen.OfferARide.route,
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""

            // Truyền tham số vào ConfirmRideScreen
            OfferARideScreen(navController = navController,userName = userName)
        }
        composable(
            Screen.OfferDetails.route,
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType },
                navArgument("estimatedDeparture") { type = NavType.StringType },
                navArgument("fromLocation") { type = NavType.StringType },
                navArgument("toLocation") { type = NavType.StringType },
                navArgument("riderName") { type = NavType.StringType },
                navArgument("riderUserId") { type = NavType.StringType },
                navArgument("cost") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Lấy các tham số từ backStackEntry
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""
            val estimatedDeparture = backStackEntry.arguments?.getString("estimatedDeparture") ?: ""
            val fromLocation = backStackEntry.arguments?.getString("fromLocation") ?: ""
            val toLocation = backStackEntry.arguments?.getString("toLocation") ?: ""
            val riderName = backStackEntry.arguments?.getString("riderName") ?: ""
            val riderUserId = backStackEntry.arguments?.getString("riderUserId") ?: ""
            val cost = backStackEntry.arguments?.getString("cost") ?: ""

            // Truyền tham số vào RideDetailsScreen
            OfferDetailsScreen(
                navController = navController,
                userName=userName,
                rideNo = rideNo,
                estimatedDeparture = estimatedDeparture,
                fromLocation = fromLocation,
                toLocation = toLocation,
                riderName = riderName,
                riderUserId = riderUserId,
                cost = cost
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
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            AddNewOfferScreen1(navController, userName)
        }
        composable(
            Screen.AddNewOffer2.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            AddNewOfferScreen2(navController, userName)
        }
        composable(
            Screen.AddNewOffer3.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            AddNewOfferScreen3(navController, userName)
        }
        composable(
            Screen.AddNewOffer4.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            AddNewOfferScreen4(navController, userName)
        }
        composable(
            Screen.AddNewOfferSuccessfully.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            AddNewOfferSuccessfullyScreen(navController, userName)
        }
        composable(
            Screen.ChatDetails.route,
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
                navArgument("friendName") { type = NavType.StringType },
                navArgument("isActive") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            val friendName = backStackEntry.arguments?.getString("friendName") ?: ""
            val isActive = backStackEntry.arguments?.getString("isActive") ?: ""
            ChatDetailsScreen(navController, userName,friendName,isActive)
        }
        composable(
            Screen.RideDetailsRating.route,
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
                navArgument("rideNo") { type = NavType.StringType },
                navArgument("riderName") { type = NavType.StringType },
                navArgument("riderUserId") { type = NavType.StringType },
                navArgument("fromLocation") { type = NavType.StringType },
                navArgument("toLocation") { type = NavType.StringType },)
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            val rideNo = backStackEntry.arguments?.getString("rideNo") ?: ""
            val riderName = backStackEntry.arguments?.getString("riderName") ?: ""
            val riderUserId = backStackEntry.arguments?.getString("riderUserId") ?: ""
            val fromLocation = backStackEntry.arguments?.getString("fromLocation") ?: ""
            val toLocation = backStackEntry.arguments?.getString("toLocation") ?: ""

            RideDetailsRatingScreen(navController, userName,rideNo,riderName,riderUserId,fromLocation,toLocation)
        }
        composable(
            Screen.ProfileView.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            ProfileViewScreen(navController, userName)
        }
        composable(
            Screen.EditProfile.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            EditProfileScreen(navController, userName)
        }
        composable(
            Screen.RideCircle.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            RideCircleScreen(navController, userName)
        }
        composable(
            Screen.PromotionRewards.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            PromotionRewardsScreen(navController, userName)
        }
        composable(
            Screen.SavedLocations.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            SavedLocationScreen(navController, userName)
        }
        composable(
            Screen.HelpSupport.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            HelpSupportScreen(navController, userName)
        }
        composable(
            Screen.ContactUs.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            ContactUsScreen(navController, userName)
        }
    }

}
