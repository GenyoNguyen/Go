    package com.example.projectse104.ui.screens.profile

    sealed class UserDataFormEvent {
        data class FullNameChanged(val fullName: String) : UserDataFormEvent()
        data class EmailChanged(val email: String) : UserDataFormEvent()
        data class PhoneNumberChanged(val phoneNumber: String) : UserDataFormEvent()
        data class LocationChanged(val location: String) : UserDataFormEvent()
        data class ProfilePicSelected(val imageUri: String) : UserDataFormEvent()
        data object Submit : UserDataFormEvent()
    }