package com.example.projectse104.ui.screens.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.domain.use_case.auth.LoginUseCase
import com.example.projectse104.domain.use_case.data.ValidationError
import com.example.projectse104.domain.use_case.user.FirebaseAuthUseCase
import com.example.projectse104.domain.use_case.data.ValidationEvent
import com.example.projectse104.utils.DataStoreSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase, // Thêm LoginUseCase
    private val sessionManager: DataStoreSessionManager
) : ViewModel() {

    var state by mutableStateOf(LoginFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return emailRegex.matches(email)
    }

    private fun isValidPassword(password: String): Boolean {
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
        if (state.email.isBlank()) {
            state = state.copy(emailError = ValidationError.EMPTY_FIELD)
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Error(Exception("Email không được để trống")))
            }
            return
        }

        if (!isValidEmail(state.email)) {
            state = state.copy(emailError = ValidationError.INVALID_EMAIL)
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Error(Exception("Định dạng email không hợp lệ")))
            }
            return
        }

        if (state.password.isBlank()) {
            state = state.copy(passwordError = ValidationError.EMPTY_FIELD)
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Error(Exception("Mật khẩu không được để trống")))
            }
            return
        }

        if (!isValidPassword(state.password)) {
            state = state.copy(passwordError = ValidationError.INVALID_PASSWORD)
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Error(Exception("Mật khẩu phải có ít nhất 6 ký tự")))
            }
            return
        }

        viewModelScope.launch {
            loginUseCase(state.email, state.password).collect { response ->
                when (response) {
                    is Response.Idle -> {}
                    is Response.Loading -> {
                        validationEventChannel.send(ValidationEvent.Loading)
                    }
                    is Response.Success -> {
                        response.data?.let { user ->
                            if (!user.is_email_verified) {
                                state = state.copy(emailError = ValidationError.EMAIL_NOT_VERIFIED)
                                validationEventChannel.send(ValidationEvent.Error(Exception("Email chưa được xác minh")))
                                return@collect
                            }
                            Log.d("LoginViewModel", "Đăng nhập thành công cho UID: ${user.firebaseUid}")
                            sessionManager.saveUserSession(
                                userId = user.id,
                                email = user.email,
                                userName = user.fullName ?: ""
                            )
                            validationEventChannel.send(ValidationEvent.Success(user.id))
                        } ?: run {
                            Log.e("LoginViewModel", "Dữ liệu người dùng null trong Response.Success")
                            validationEventChannel.send(ValidationEvent.Error(Exception("Dữ liệu người dùng bị thiếu")))
                        }
                    }
                    is Response.Failure -> {
                        Log.e("LoginViewModel", "Đăng nhập thất bại: ${response.e?.message}")
                        val errorMessage = response.e?.message ?: "Lỗi không xác định"
                        when {
                            errorMessage.contains("User with this email does not exist") -> {
                                state = state.copy(emailError = ValidationError.INVALID_EMAIL)
                            }
                            errorMessage.contains("Incorrect password") -> {
                                state = state.copy(passwordError = ValidationError.INVALID_PASSWORD)
                            }
                            errorMessage.contains("Network error") -> {
                                validationEventChannel.send(ValidationEvent.Error(Exception("Lỗi mạng")))
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