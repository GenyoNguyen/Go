package com.example.projectse104.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.use_case.user.ChangePasswordUseCase
import com.example.projectse104.domain.use_case.user.VerifyCurrentPasswordUseCase
import com.example.projectse104.ui.screens.profile.ChangePasswordEvent
import com.example.projectse104.domain.use_case.data.ValidationError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val verifyCurrentPasswordUseCase: VerifyCurrentPasswordUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(
        ChangePasswordFormState(
            userId = savedStateHandle.get<String>(USER_ID_FIELD) ?: ""
        )
    )

    private val changePasswordEventChannel = Channel<ChangePasswordEvent>()
    val changePasswordEvents = changePasswordEventChannel.receiveAsFlow()

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun onEvent(event: ChangePasswordFormEvent) {
        when (event) {
            is ChangePasswordFormEvent.CurrentPasswordChanged -> {
                state = state.copy(currentPassword = event.password, currentPasswordError = null)
            }
            is ChangePasswordFormEvent.NewPasswordChanged -> {
                state = state.copy(newPassword = event.password, newPasswordError = null)
            }
            is ChangePasswordFormEvent.ConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = event.password, confirmPasswordError = null)
            }
            is ChangePasswordFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        if (state.currentPassword.isBlank()) {
            state = state.copy(currentPasswordError = ValidationError.EMPTY_FIELD)
            viewModelScope.launch {
                changePasswordEventChannel.send(ChangePasswordEvent.Error(Exception("Mật khẩu hiện tại không được để trống")))
            }
            return
        }

        if (state.newPassword.isBlank()) {
            state = state.copy(newPasswordError = ValidationError.EMPTY_FIELD)
            viewModelScope.launch {
                changePasswordEventChannel.send(ChangePasswordEvent.Error(Exception("Mật khẩu mới không được để trống")))
            }
            return
        }

        if (!isValidPassword(state.newPassword)) {
            state = state.copy(newPasswordError = ValidationError.INVALID_PASSWORD)
            viewModelScope.launch {
                changePasswordEventChannel.send(ChangePasswordEvent.Error(Exception("Mật khẩu mới phải có ít nhất 6 ký tự")))
            }
            return
        }

        if (state.confirmPassword.isBlank()) {
            state = state.copy(confirmPasswordError = ValidationError.EMPTY_FIELD)
            viewModelScope.launch {
                changePasswordEventChannel.send(ChangePasswordEvent.Error(Exception("Xác nhận mật khẩu không được để trống")))
            }
            return
        }

        if (state.newPassword != state.confirmPassword) {
            state = state.copy(confirmPasswordError = ValidationError.PASSWORD_MISMATCH)
            viewModelScope.launch {
                changePasswordEventChannel.send(ChangePasswordEvent.Error(Exception("Xác nhận mật khẩu không khớp")))
            }
            return
        }

        viewModelScope.launch {
            verifyCurrentPasswordUseCase(state.userId, state.currentPassword).collect { response ->
                when (response) {
                    is Response.Success -> {
                        // Mật khẩu hiện tại đúng, tiến hành đổi mật khẩu
                        changePasswordUseCase(state.userId, state.newPassword).collect { changeResponse ->
                            when (changeResponse) {
                                is Response.Loading -> {
                                    changePasswordEventChannel.send(ChangePasswordEvent.Loading)
                                }
                                is Response.Success -> {
                                    changePasswordEventChannel.send(ChangePasswordEvent.Success)
                                    state = state.copy(
                                        currentPassword = "",
                                        newPassword = "",
                                        confirmPassword = "",
                                        currentPasswordError = null,
                                        newPasswordError = null,
                                        confirmPasswordError = null
                                    )
                                }
                                is Response.Failure -> {
                                    changePasswordEventChannel.send(ChangePasswordEvent.Error(changeResponse.e))
                                }
                                is Response.Idle -> {}
                            }
                        }
                    }
                    is Response.Failure -> {
                        state = state.copy(currentPasswordError = ValidationError.INVALID_PASSWORD)
                        changePasswordEventChannel.send(ChangePasswordEvent.Error(response.e))
                    }
                    is Response.Loading -> {
                        changePasswordEventChannel.send(ChangePasswordEvent.Loading)
                    }
                    is Response.Idle -> {}
                }
            }
        }
    }
}