package com.example.projectse104.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object OnBoarding1 : Screen("onboarding1")
    object OnBoarding2 : Screen("onboarding2")
    object OnBoarding3 : Screen("onboarding3")
    object SignIn : Screen("sign_in")
    object SignUp : Screen("sign_up")
    object Home : Screen("home/{userId}")
    object SignUpAndSignIn : Screen("sign_up_and_sign_in")
    object ForgotPassword : Screen("forgot_password")
    object VerifyEmail : Screen("verify_email")
    object NewPassword : Screen("new_password")
    object ResetPasswordSuccessful : Screen("reset_password_successful")
    object LoginSuccessful : Screen("login_successful")
    object Chat : Screen("chat/{userId}")
    object History : Screen("history/{userId}")
    object Profile : Screen("profile/{userId}")
    object FindARide : Screen("find_a_ride/{userId}") // ✅ Thêm route mới
    object ConfirmRide : Screen("confirm_ride/{riderName}/{rideID}/{userId}") // Thêm tham số userId và rideID
    object BookingSuccessful : Screen("booking_successful/{userId}") // ✅ Thêm màn hình RideDetails
    object OfferARide : Screen("offer_a_ride/{userId}") // ✅ Thêm màn hình RideDetails
    object OfferDetails : Screen("offer_details/{userId}/{rideNo}/{estimatedDeparture}/{fromLocation}/{toLocation}/{riderName}/{riderUserId}/{cost}") // ✅ Thêm màn hình RideDetails
    object ConfirmRequest : Screen("confirm_request/{passengerName}/{rideID}/{riderName}") // ✅ Thêm màn hình RideDetails
    object RideDetails : Screen("ride_details/{userId}/{rideNo}/{estimatedDeparture}/{fromLocation}/{toLocation}/{riderName}/{riderUserId}/{passengerName}/{passengerUserId}/{cost}/{addGoButton}")
    object AddNewOffer1 : Screen("add_new_offer1/{userId}")
    object AddNewOffer2 : Screen("add_new_offer2/{userId}")
    object AddNewOffer3 : Screen("add_new_offer3/{userId}")
    object AddNewOffer4 : Screen("add_new_offer4/{userId}")
    object AddNewOfferSuccessfully: Screen("add_new_offer_successfully/{userId}")
    object ChatDetails: Screen("chat_details/{userId}/{friendName}/{isActive}")
    object RideDetailsHistory : Screen("ride_details_history/{userId}/{rideNo}/{estimatedDeparture}/{fromLocation}/{toLocation}/{riderName}/{riderUserId}/{passengerName}/{passengerUserId}/{cost}")
    object RideDetailsRating : Screen("ride_details_rating/{userId}/{rideNo}/{riderName}/{riderUserId}/{fromLocation}/{toLocation}")
    object ProfileView : Screen("profile_view/{userId}")
    object EditProfile : Screen("edit_profile/{userId}")
    object RideCircle: Screen("ride_circle/{userId}")
    object PromotionRewards: Screen("promotion_rewards/{userId}")
    object SavedLocations: Screen("saved_locations/{userId}")
    object HelpSupport: Screen("help_support/{userId}")
    object ContactUs: Screen("contact_us/{userId}")

}
