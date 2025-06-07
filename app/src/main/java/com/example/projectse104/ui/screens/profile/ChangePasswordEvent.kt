package com.example.projectse104.ui.screens.profile

sealed class ChangePasswordEvent {
    data object Success : ChangePasswordEvent()
    data class Error(val e: Exception?) : ChangePasswordEvent()
    data object Loading : ChangePasswordEvent()
}
