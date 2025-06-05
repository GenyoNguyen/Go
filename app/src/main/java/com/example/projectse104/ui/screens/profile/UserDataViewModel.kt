package com.example.projectse104.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.domain.use_case.data.ValidationError
import com.example.projectse104.domain.use_case.user.UpdateUserUseCase
import com.example.projectse104.domain.use_case.user.UploadProfilePicUseCase
import com.example.projectse104.domain.use_case.validation.ValidateEmail
import com.example.projectse104.domain.use_case.validation.ValidateFullName
import com.example.projectse104.domain.use_case.validation.ValidateLocation
import com.example.projectse104.domain.use_case.validation.ValidatePhoneNumber
import com.example.projectse104.ui.screens.profile.UserDataViewModel.ValidationEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val validateFullName: ValidateFullName,
    private val validateEmail: ValidateEmail,
    private val validatePhoneNumber: ValidatePhoneNumber,
    private val validateLocation: ValidateLocation,
    private val updateUserUseCase: UpdateUserUseCase,
    private val uploadProfilePicUseCase: UploadProfilePicUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId: String = savedStateHandle.get<String>("userId").toString()

    var state by mutableStateOf(UserDataFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun setProfilePicUri(url: String?) {
        state = state.copy(profilePicUri = url)
        Log.d("UserDataViewModel", "Profile pic URI updated: $url")
    }

    fun onEvent(event: UserDataFormEvent) {
        when (event) {
            is UserDataFormEvent.FullNameChanged -> {
                state = state.copy(fullName = event.fullName)
            }

            is UserDataFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is UserDataFormEvent.LocationChanged -> {
                state = state.copy(location = event.location)
            }

            is UserDataFormEvent.PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.phoneNumber)
            }

            is UserDataFormEvent.ProfilePicSelected -> {
                // Chỉ hiển thị ảnh, không upload ở đây
                state = state.copy(
                    profilePicUri = event.imageUri,
                    profilePicError = null
                )
                Log.d("UserDataViewModel", "Ảnh được chọn để hiển thị: ${event.imageUri}")
            }

            UserDataFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun uploadProfilePictureWithPath(filePath: String) {
        state = state.copy(isUploadingProfilePic = true)
        uploadProfilePicture(filePath)
    }

    private fun uploadProfilePicture(imageUri: String) {
        viewModelScope.launch {
            // Kiểm tra xem imageUri có phải là đường dẫn tệp hợp lệ không
            if (imageUri.isBlank()) {
                state = state.copy(
                    profilePicError = ValidationError.CANT_GET_PIC,
                    isUploadingProfilePic = false
                )
                validationEventChannel.send(Error(Exception("URI không hợp lệ: $imageUri")))
                return@launch
            }

            uploadProfilePicUseCase(userId, imageUri).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        // Không cần thay đổi profilePicUri ở đây vì đã hiển thị ảnh local
                        validationEventChannel.send(Loading)
                    }
                    is Response.Success -> {
                        // Cập nhật với URL từ server sau khi upload thành công
                        state = state.copy(
                            profilePicUri = response.data, // URL từ server
                            isUploadingProfilePic = false,
                            profilePicError = null
                        )
                        validationEventChannel.send(Success)
                        Log.d("UserDataViewModel", "Upload thành công, URL mới: ${state.profilePicUri}")
                    }
                    is Response.Failure -> {
                        state = state.copy(
                            profilePicError = ValidationError.CANT_GET_PIC,
                            isUploadingProfilePic = false
                        )
                        Log.e("UploadProfilePic", "Tải lên thất bại: ${response.e?.message}", response.e)
                        validationEventChannel.send(Error(response.e))
                    }
                    Response.Idle -> {}
                }
            }
        }
    }

    private fun submitData() {
        val fullNameResult = validateFullName.execute(state.fullName)
        val emailResult = validateEmail.execute(state.email)
        val phoneNumberResult = validatePhoneNumber.execute(state.phoneNumber)
        val locationResult = validateLocation.execute(state.location)

        val hasError = listOf(
            fullNameResult,
            emailResult,
            phoneNumberResult,
            locationResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                fullNameError = fullNameResult.error,
                emailError = emailResult.error,
                phoneNumberError = phoneNumberResult.error,
                locationError = locationResult.error
            )
            println("Lỗi khi gửi biểu mẫu")
            return
        }
        viewModelScope.launch {
            println("Gửi dữ liệu để cập nhật")
            val updateData = mapOf(
                "id" to userId,
                "fullName" to state.fullName,
                "email" to state.email,
                "phoneNumber" to state.phoneNumber,
                "location" to state.location,
                // Bao gồm profilePic nếu có
                "profilePic" to (state.profilePicUri ?: "")
            )
            println("Dữ liệu cập nhật: $updateData")
            updateUserUseCase(updateData).collect {
                when (it) {
                    is Response.Loading -> {
                        validationEventChannel.send(Loading)
                    }
                    is Response.Success -> {
                        validationEventChannel.send(Success)
                    }
                    is Response.Failure -> {
                        validationEventChannel.send(Error(it.e))
                    }
                    else -> {}
                }
            }
        }
    }

    sealed class ValidationEvent {
        data object Loading : ValidationEvent()
        data object Success : ValidationEvent()
        data class Error(val e: Exception?) : ValidationEvent()
    }
}