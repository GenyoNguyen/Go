package com.example.projectse104.domain.use_case.user

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseAuthUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) {
    suspend fun sendEmailOTP(email: String, password: String): Flow<Response<FirebaseUser>> {
        return firebaseAuthRepository.sendEmailOTP(email, password)
    }

    suspend fun verifyEmail(user: FirebaseUser): Flow<Response<Unit>> {
        return firebaseAuthRepository.verifyEmail(user)
    }

    suspend fun signInWithEmail(email: String, password: String): Flow<Response<FirebaseUser>> {
        return firebaseAuthRepository.signInWithEmail(email, password)
    }
}