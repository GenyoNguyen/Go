package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthRepository {

    override suspend fun sendEmailOTP(email: String, password: String): Flow<Response<FirebaseUser>> = flow {
        emit(Response.Loading)
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                user.sendEmailVerification().await()
                emit(Response.Success(user))
            } else {
                emit(Response.Failure(Exception("User creation failed")))
            }
        } catch (e: Exception) {
            emit(Response.Failure(Exception(e.message ?: "Unknown error")))
        }
    }

    override suspend fun verifyEmail(user: FirebaseUser): Flow<Response<Unit>> = flow {
        emit(Response.Loading)
        try {
            user.reload().await()
            if (user.isEmailVerified) {
                emit(Response.Success(Unit))
            } else {
                emit(Response.Failure(Exception("Email not verified")))
            }
        } catch (e: Exception) {
            emit(Response.Failure(Exception(e.message ?: "Unknown error")))
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): Flow<Response<FirebaseUser>> = flow {
        emit(Response.Loading)
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null && user.isEmailVerified) {
                emit(Response.Success(user))
            } else {
                emit(Response.Failure(Exception("Email not verified or user not found")))
            }
        } catch (e: Exception) {
            emit(Response.Failure(Exception(e.message ?: "Unknown error")))
        }
    }
}