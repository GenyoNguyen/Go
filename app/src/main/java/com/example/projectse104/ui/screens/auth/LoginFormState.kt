package com.example.projectse104.ui.screens.auth

import com.example.projectse104.domain.use_case.data.ValidationError

data class LoginFormState(
    val email: String = "",
    val emailError: ValidationError? = null,
    val password: String = "",
    val passwordError: ValidationError? = null
)
