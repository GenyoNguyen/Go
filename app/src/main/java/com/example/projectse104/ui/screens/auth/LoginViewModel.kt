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
                        when {
                            errorMessage.contains("Invalid email") -> {
                                state = state.copy(emailError = ValidationError.INVALID_EMAIL)
                            }
                            errorMessage.contains("Invalid password") -> {
                                state = state.copy(passwordError = ValidationError.INVALID_PASSWORD)
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
