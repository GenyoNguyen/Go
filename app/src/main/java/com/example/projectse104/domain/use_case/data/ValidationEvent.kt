package com.example.projectse104.domain.use_case.data

sealed class ValidationEvent {
    data object Loading : ValidationEvent()
    data class Success(val userId: String) : ValidationEvent()
    data class Error(val e: Exception?) : ValidationEvent()
}