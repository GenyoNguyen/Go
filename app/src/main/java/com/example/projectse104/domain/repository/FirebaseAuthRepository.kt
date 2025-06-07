package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthRepository {
    suspend fun sendEmailOTP(email: String, password: String): Flow<Response<FirebaseUser>>
    suspend fun verifyEmail(user: FirebaseUser): Flow<Response<Unit>>
    suspend fun signInWithEmail(email: String, password: String): Flow<Response<FirebaseUser>>
}