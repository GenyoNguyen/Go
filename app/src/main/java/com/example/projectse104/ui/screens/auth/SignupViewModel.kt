package com.example.projectse104.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.use_case.user.AddUserUseCase
import com.example.projectse104.domain.use_case.user.FirebaseAuthUseCase
import com.example.projectse104.domain.use_case.user.UpdateEmailVerificationUseCase
import com.example.projectse104.domain.use_case.validation.ValidateConfirmPassword
import com.example.projectse104.domain.use_case.validation.ValidateEmail
import com.example.projectse104.domain.use_case.validation.ValidateFullName
import com.example.projectse104.domain.use_case.validation.ValidatePassword
import com.example.projectse104.domain.use_case.data.ValidationEvent
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.tasks.await
import org.mindrot.jbcrypt.BCrypt
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val validateFullName: ValidateFullName,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword,
    private val addUserUseCase: AddUserUseCase,
    private val firebaseAuthUseCase: FirebaseAuthUseCase,
    private val updateEmailVerificationUseCase: UpdateEmailVerificationUseCase
) : ViewModel() {

    var state by mutableStateOf(SignupFormState())
    var firebaseUser by mutableStateOf<FirebaseUser?>(null)

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: SignupFormEvent) {
        when (event) {
            is SignupFormEvent.FullNameChanged -> state = state.copy(fullName = event.fullName)
            is SignupFormEvent.EmailChanged -> state = state.copy(email = event.email)
            is SignupFormEvent.PasswordChanged -> state = state.copy(password = event.password)
            is SignupFormEvent.ConfirmPasswordChanged -> state = state.copy(confirmPassword = event.confirmPassword)
            is SignupFormEvent.Submit -> submitData()
            is SignupFormEvent.VerifyEmail -> verifyEmail()
        }
    }

    private fun submitData() {
        val fullNameResult = validateFullName.execute(state.fullName)
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val confirmPasswordResult = validateConfirmPassword.execute(state.password, state.confirmPassword)

        val hasError = listOf(fullNameResult, emailResult, passwordResult, confirmPasswordResult).any { !it.successful }

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
            firebaseAuthUseCase.sendEmailOTP(state.email, state.password).collect { response ->
                when (response) {
                    is Response.Loading -> validationEventChannel.send(ValidationEvent.Loading)
                    is Response.Success -> {
                        response.data?.let { user ->
                            firebaseUser = user
                            Log.d("SignupViewModel", "FirebaseUser UID: ${user.uid}")
                            saveUserToSupabase(user.uid)
                            validationEventChannel.send(ValidationEvent.PendingVerification)
                        } ?: run {
                            Log.e("SignupViewModel", "FirebaseUser is null")
                            validationEventChannel.send(ValidationEvent.Error(Exception("User creation failed")))
                        }
                    }
                    is Response.Failure -> {
                        Log.e("SignupViewModel", "Sign-up failed: ${response.e?.message}")
                        validationEventChannel.send(ValidationEvent.Error(Exception(response.e?.message ?: "Unknown error")))
                    }
                    is Response.Idle -> {}
                }
            }
        }
    }

    // Trong SignupViewModel.saveUserToSupabase
    private suspend fun saveUserToSupabase(userId: String) { // Mã hóa mật khẩu
        val user = User(
            id = UUID.randomUUID().toString(),
            fullName = state.fullName,
            email = state.email,
            password = state.password, // Lưu mật khẩu mã hóa
            phoneNumber = null,
            location = null,
            profilePic = "https://ouanezsrnbseuupwngww.supabase.co/storage/v1/object/public/profile-picture//avatar_1.png",
            overallRating = 5.0f,
            coins = 100,
            userCode = UUID.randomUUID().toString().substring(0, 8),
            vehicleId = null,
            is_email_verified = false,
            firebaseUid = userId
        )
        try {
            Log.d("SignupViewModel", "User object: $user")
            addUserUseCase(user).collect { response ->
                when (response) {
                    is Response.Success -> {
                        Log.d("SignupViewModel", "User saved to Supabase successfully")
                    }
                    is Response.Failure -> {
                        Log.e("SignupViewModel", "Failed to save user to Supabase: ${response.e?.message}", response.e)
                        firebaseUser?.delete()?.await()
                        validationEventChannel.send(ValidationEvent.Error(Exception(response.e?.message ?: "Failed to save user")))
                    }
                    else -> {
                        Log.e("SignupViewModel", "Unexpected response from addUserUseCase")
                        validationEventChannel.send(ValidationEvent.Error(Exception("Unexpected response")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("SignupViewModel", "Exception in saveUserToSupabase: ${e.message}", e)
            firebaseUser?.delete()?.await()
            validationEventChannel.send(ValidationEvent.Error(Exception("Failed to save user: ${e.message}")))
        }
    }

    private fun verifyEmail() {
        firebaseUser?.let { user ->
            viewModelScope.launch {
                try {
                    firebaseAuthUseCase.verifyEmail(user).collect { response ->
                        when (response) {
                            is Response.Loading -> validationEventChannel.send(ValidationEvent.Loading)
                            is Response.Success -> {
                                Log.d("SignupViewModel", "Email verified successfully for UID: ${user.uid}")
                                updateEmailVerificationStatus(user.uid)
                                validationEventChannel.send(ValidationEvent.Success(user.uid))
                            }
                            is Response.Failure -> {
                                Log.e("SignupViewModel", "Email verification failed: ${response.e?.message}", response.e)
                                validationEventChannel.send(ValidationEvent.Error(Exception(response.e?.message ?: "Email not verified")))
                            }
                            is Response.Idle -> {}
                        }
                    }
                } catch (e: Exception) {
                    Log.e("SignupViewModel", "Exception in verifyEmail: ${e.message}", e)
                    validationEventChannel.send(ValidationEvent.Error(Exception("Email verification failed: ${e.message}")))
                }
            }
        } ?: run {
            Log.e("SignupViewModel", "FirebaseUser is null during email verification")
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Error(Exception("User not found")))
            }
        }
    }

    private suspend fun updateEmailVerificationStatus(userId: String) {
        Log.d("SignupViewModel", "Updating email verification status for Firebase UID: $userId")
        updateEmailVerificationUseCase(userId, true).collect { response ->
            when (response) {
                is Response.Success -> {
                    Log.d("SignupViewModel", "Email verification status updated successfully")
                }
                is Response.Failure -> {
                    Log.e("SignupViewModel", "Failed to update verification status: ${response.e?.message}", response.e)
                    validationEventChannel.send(ValidationEvent.Error(Exception(response.e?.message ?: "Failed to update verification status")))
                }
                else -> {}
            }
        }
    }
}