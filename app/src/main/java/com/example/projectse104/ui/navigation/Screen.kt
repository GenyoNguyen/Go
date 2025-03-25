package com.example.projectse104.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object OnBoarding1 : Screen("onboarding1")
    object OnBoarding2 : Screen("onboarding2")
    object OnBoarding3 : Screen("onboarding3")
    object SignIn : Screen("sign_in")
    object SignUp : Screen("sign_up")
    object Home : Screen("home/{userName}")
    object SignUpAndSignIn : Screen("sign_up_and_sign_in")
    object ForgotPassword : Screen("forgot_password")
    object VerifyEmail : Screen("verify_email")
    object NewPassword : Screen("new_password")
    object ResetPasswordSuccessful : Screen("reset_password_successful")
    object LoginSuccessful : Screen("login_successful")
    object Chat : Screen("chat/{userName}")
    object History : Screen("history/{userName}")
    object Profile : Screen("profile/{userName}")
    object FindARide : Screen("find_a_ride/{userName}") // ✅ Thêm route mới
    object ConfirmRide : Screen("confirm_ride/{riderName}/{rideID}/{userName}") // Thêm tham số userName và rideID
    object BookingSuccessful : Screen("booking_successful/{userName}") // ✅ Thêm màn hình RideDetails
    object OfferARide : Screen("offer_a_ride/{userName}") // ✅ Thêm màn hình RideDetails
    object OfferDetails : Screen("offer_details/{userName}/{rideNo}/{estimatedDeparture}/{fromLocation}/{toLocation}/{riderName}/{riderUserId}/{cost}") // ✅ Thêm màn hình RideDetails
    object ConfirmRequest : Screen("confirm_request/{passengerName}/{rideID}/{riderName}") // ✅ Thêm màn hình RideDetails
    object RideDetails : Screen("ride_details/{userName}/{rideNo}/{estimatedDeparture}/{fromLocation}/{toLocation}/{riderName}/{riderUserId}/{passengerName}/{passengerUserId}/{cost}/{addGoButton}")
    object AddNewOffer1 : Screen("add_new_offer1/{userName}")
    object AddNewOffer2 : Screen("add_new_offer2/{userName}")
    object AddNewOffer3 : Screen("add_new_offer3/{userName}")
    object AddNewOffer4 : Screen("add_new_offer4/{userName}")
    object AddNewOfferSuccessfully: Screen("add_new_offer_successfully/{userName}")
    object ChatDetails: Screen("chat_details/{userName}/{friendName}/{isActive}")
    object RideDetailsHistory : Screen("ride_details_history/{userName}/{rideNo}/{estimatedDeparture}/{fromLocation}/{toLocation}/{riderName}/{riderUserId}/{passengerName}/{passengerUserId}/{cost}")
    object RideDetailsRating : Screen("ride_details_rating/{userName}/{rideNo}/{riderName}/{riderUserId}/{fromLocation}/{toLocation}")
    object ProfileView : Screen("profile_view/{userName}")
    object EditProfile : Screen("edit_profile/{userName}")
    object RideCircle: Screen("ride_circle/{userName}")
    object PromotionRewards: Screen("promotion_rewards/{userName}")
    object SavedLocations: Screen("saved_locations/{userName}")
    object HelpSupport: Screen("help_support/{userName}")
    object ContactUs: Screen("contact_us/{userName}")

}
