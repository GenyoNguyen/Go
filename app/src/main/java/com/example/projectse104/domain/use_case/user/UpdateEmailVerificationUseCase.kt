package com.example.projectse104.domain.use_case.user

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import android.util.Log
import javax.inject.Inject

class UpdateEmailVerificationUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(firebaseUid: String, isVerified: Boolean): Flow<Response<Unit>> = flow {
        emit(Response.Loading)
        try {
            Log.d("UpdateEmailVerificationUseCase", "Updating is_email_verified for Firebase UID: $firebaseUid")
            val result = repository.updateEmailVerificationStatus(firebaseUid, isVerified)
            when (result) {
                is Response.Success -> {
                    Log.d("UpdateEmailVerificationUseCase", "Update successful")
                    emit(Response.Success(Unit))
                }
                is Response.Failure -> {
                    Log.e("UpdateEmailVerificationUseCase", "Update failed: ${result.e?.message}", result.e)
                    emit(Response.Failure(result.e))
                }
                else -> emit(Response.Failure(Exception("Unexpected error during update")))
            }
        } catch (e: Exception) {
            Log.e("UpdateEmailVerificationUseCase", "Exception: ${e.message}", e)
            emit(Response.Failure(Exception(e.message ?: "Failed to update verification status")))
        }
    }
}