package com.example.projectse104.ui.screens.auth

import com.example.projectse104.domain.use_case.data.ValidationError

data class SignupFormState(
    val fullName: String = "",
    val fullNameError: ValidationError? = null,
    val email: String = "",
    val emailError: ValidationError? = null,
    val password: String = "",
    val passwordError: ValidationError? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: ValidationError? = null
)
