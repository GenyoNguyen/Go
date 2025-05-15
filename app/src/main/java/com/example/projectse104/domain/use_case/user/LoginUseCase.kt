package com.example.projectse104.domain.use_case.auth

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.LoginResponse
import com.example.projectse104.domain.repository.UserLogin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userLogin: UserLogin
) {
    operator fun invoke(email: String, password: String): Flow<LoginResponse> = flow {
        emit(Response.Loading)
        try {
            val loginResult = userLogin.login(email, password)
            when (loginResult) {
                is Response.Success -> emit(Response.Success(loginResult.data))
                is Response.Failure -> emit(Response.Failure(loginResult.e))
                else -> emit(Response.Failure(Exception("Unexpected error during login")))
            }
        } catch (e: Exception) {
            emit(Response.Failure(Exception(e.message ?: "Unknown error")))
        }
    }
}