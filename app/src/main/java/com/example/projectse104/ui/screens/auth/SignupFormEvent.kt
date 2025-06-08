package com.example.projectse104.ui.screens.auth

sealed class SignupFormEvent {
    data class FullNameChanged(val fullName: String) : SignupFormEvent()
    data class EmailChanged(val email: String) : SignupFormEvent()
    data class PasswordChanged(val password: String) : SignupFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignupFormEvent()
    data object Submit : SignupFormEvent()
    data object VerifyEmail : SignupFormEvent()
}