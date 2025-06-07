package com.example.projectse104.domain.use_case.user

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.UserLogin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyCurrentPasswordUseCase @Inject constructor(
    private val userLogin: UserLogin
) {
    operator fun invoke(
        userId: String,
        currentPassword: String
    ): Flow<Response<Unit>> = flow {
        emit(Response.Loading)
        try {
            val result = userLogin.verifyPassword(userId, currentPassword)
            when (result) {
                is Response.Success -> emit(Response.Success(Unit))
                is Response.Failure -> emit(Response.Failure(result.e))
                else -> emit(Response.Failure(Exception("Lỗi không xác định khi kiểm tra mật khẩu")))
            }
        } catch (e: Exception) {
            emit(Response.Failure(Exception(e.message ?: "Lỗi không xác định")))
        }
    }
}
