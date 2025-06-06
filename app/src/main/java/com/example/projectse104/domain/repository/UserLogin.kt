package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User

typealias LoginResponse = Response<User>

interface UserLogin {
    suspend fun login(email: String, password: String): LoginResponse
    suspend fun updatePassword(userId: String, newPassword: String): Response<Unit>
    suspend fun verifyPassword(userId: String, currentPassword: String): Response<Unit>
}