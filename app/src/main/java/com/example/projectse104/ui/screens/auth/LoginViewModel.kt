package com.example.projectse104.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.domain.use_case.auth.LoginUseCase
import com.example.projectse104.domain.use_case.data.ValidationError
import com.example.projectse104.domain.use_case.data.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    // Hàm kiểm tra định dạng email
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return emailRegex.matches(email)
    }

    // Hàm kiểm tra định dạng mật khẩu
    private fun isValidPassword(password: String): Boolean {
        // Mật khẩu phải có ít nhất 6 ký tự
        return password.length >= 6
    }

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                state = state.copy(email = event.email, emailError = null)
            }
            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password, passwordError = null)
            }
            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        // Kiểm tra email trống
        if (state.email.isBlank()) {
            state = state.copy(emailError = ValidationError.EMPTY_FIELD)
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Error(Exception("Email cannot be empty")))
            }
            return
        }

        // Kiểm tra định dạng email
        if (!isValidEmail(state.email)) {
            state = state.copy(emailError = ValidationError.INVALID_EMAIL)
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Error(Exception("Invalid email format")))
            }
            return
        }

        // Kiểm tra mật khẩu trống
        if (state.password.isBlank()) {
            state = state.copy(passwordError = ValidationError.EMPTY_FIELD)
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Error(Exception("Password cannot be empty")))
            }
            return
        }

        // Kiểm tra định dạng mật khẩu
        if (!isValidPassword(state.password)) {
            state = state.copy(passwordError = ValidationError.INVALID_PASSWORD)
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Error(Exception("Password must be at least 6 characters")))
            }
            return
        }

        // Nếu tất cả hợp lệ, gửi yêu cầu lên server
        viewModelScope.launch {
            loginUseCase(state.email, state.password).collect { response ->
                when (response) {
                    is Response.Idle -> {}
                    is Response.Loading -> {
                        validationEventChannel.send(ValidationEvent.Loading)
                    }
                    is Response.Success -> {
                        val userId = response.data?.id // Kiểm tra null an toàn
                        if (userId != null) {
                            validationEventChannel.send(ValidationEvent.Success(userId))
                        } else {
                            validationEventChannel.send(ValidationEvent.Error(Exception("User data is missing")))
                        }
                    }
                    is Response.Failure -> {
                        val errorMessage = response.e?.message ?: "Unknown error"
                        // Xử lý lỗi từ server
                        when {
                            errorMessage.contains("Invalid password") -> {
                                state = state.copy(passwordError = ValidationError.INVALID_PASSWORD)
                            }
                            errorMessage.contains("Email cannot be empty") -> {
                                state = state.copy(emailError = ValidationError.EMPTY_FIELD)
                            }
                            errorMessage.contains("Invalid email") -> {
                                state = state.copy(emailError = ValidationError.INVALID_EMAIL)
                            }
                            else -> {
                                validationEventChannel.send(ValidationEvent.Error(Exception(errorMessage)))
                            }
                        }
                    }
                }
            }
        }
    }
}