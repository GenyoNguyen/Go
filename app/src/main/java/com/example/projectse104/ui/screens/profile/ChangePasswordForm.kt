package com.example.projectse104.ui.screens.profile

import com.example.projectse104.domain.use_case.data.ValidationError

data class ChangePasswordFormState(
    val userId: String = "",
    val currentPassword: String = "",
    val currentPasswordError: ValidationError? = null,
    val newPassword: String = "",
    val newPasswordError: ValidationError? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: ValidationError? = null
)

sealed class ChangePasswordFormEvent {
    data class CurrentPasswordChanged(val password: String) : ChangePasswordFormEvent()
    data class NewPasswordChanged(val password: String) : ChangePasswordFormEvent()
    data class ConfirmPasswordChanged(val password: String) : ChangePasswordFormEvent()
    data object Submit : ChangePasswordFormEvent()
}
