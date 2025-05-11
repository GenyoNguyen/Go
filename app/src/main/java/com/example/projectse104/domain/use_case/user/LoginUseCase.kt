package com.example.projectse104.domain.use_case.auth

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.repository.LoginResponse
import com.example.projectse104.domain.repository.UserLogin
import com.example.projectse104.domain.use_case.data.ValidationError
import com.example.projectse104.domain.use_case.validation.ValidateEmail
import com.example.projectse104.domain.use_case.validation.ValidatePassword
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userLogin: UserLogin,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword
) {
    operator fun invoke(email: String, password: String): Flow<LoginResponse> = flow {
        emit(Response.Loading)
        try {
            // Validate email
            val emailResult = validateEmail.execute(email)
            if (!emailResult.successful) {
                val errorMessage = when (emailResult.error) {
                    ValidationError.EMPTY_FIELD -> "Email cannot be empty"
                    ValidationError.INVALID_EMAIL -> "Invalid email format"
                    else -> "Invalid email"
                }
                emit(Response.Failure(Exception(errorMessage)))
                return@flow
            }

            // Validate password
            val passwordResult = validatePassword.execute(password)
            if (!passwordResult.successful) {
                val errorMessage = when (passwordResult.error) {
                    ValidationError.EMPTY_FIELD -> "Password cannot be empty"
                    ValidationError.INVALID_PASSWORD -> "Invalid password format"
                    else -> "Invalid password"
                }
                emit(Response.Failure(Exception(errorMessage)))
                return@flow
            }

            // Gọi repository để đăng nhập
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