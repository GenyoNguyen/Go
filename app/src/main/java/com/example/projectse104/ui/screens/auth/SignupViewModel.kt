package com.example.projectse104.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.use_case.user.AddUserUseCase
import com.example.projectse104.domain.use_case.validation.ValidateConfirmPassword
import com.example.projectse104.domain.use_case.validation.ValidateEmail
import com.example.projectse104.domain.use_case.validation.ValidateFullName
import com.example.projectse104.domain.use_case.validation.ValidatePassword
import com.example.projectse104.domain.use_case.data.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val validateFullName: ValidateFullName,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword,
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {

    var state by mutableStateOf(SignupFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: SignupFormEvent) {
        when (event) {
            is SignupFormEvent.FullNameChanged -> {
                state = state.copy(fullName = event.fullName)
            }

            is SignupFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is SignupFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is SignupFormEvent.ConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = event.confirmPassword)
            }

            is SignupFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val fullNameResult = validateFullName.execute(state.fullName)
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val confirmPasswordResult =
            validateConfirmPassword.execute(state.password, state.confirmPassword)

        val hasError = listOf(
            fullNameResult,
            emailResult,
            passwordResult,
            confirmPasswordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                fullNameError = fullNameResult.error,
                emailError = emailResult.error,
                passwordError = passwordResult.error,
                confirmPasswordError = confirmPasswordResult.error
            )
            return
        }

        viewModelScope.launch {
            println("Email submitted: ${state.email}")
            val userId = UUID.randomUUID().toString() // Tạo userId
            val user = User(
                id = userId,
                fullName = state.fullName,
                email = state.email,
                password = state.password,
                phoneNumber = null,
                location = null,
                profilePic = null,
                overallRating = 0.0f,
                coins = 0,
                userCode = UUID.randomUUID().toString().substring(0, 8),
                vehicleId = null
            )
            addUserUseCase(user).collect { response ->
                when (response) {
                    is Response.Idle -> {}
                    is Response.Loading -> {
                        validationEventChannel.send(ValidationEvent.Loading)
                    }

                    is Response.Success -> {
                        validationEventChannel.send(ValidationEvent.Success(userId)) // Truyền userId
                    }

                    is Response.Failure -> {
                        val errorMessage = response.e?.message ?: "Unknown error"
                        validationEventChannel.send(ValidationEvent.Error(Exception(errorMessage)))
                    }
                }
            }
        }
    }
}