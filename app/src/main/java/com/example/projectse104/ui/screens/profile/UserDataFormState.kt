package com.example.projectse104.ui.screens.profile

import com.example.projectse104.domain.use_case.data.ValidationError

data class UserDataFormState(
    val fullName: String = "",
    val fullNameError: ValidationError? = null,
    val email: String = "",
    val emailError: ValidationError? = null,
    val phoneNumber: String = "",
    val phoneNumberError: ValidationError? = null,
    val location: String = "",
    val locationError: ValidationError? = null,
)
