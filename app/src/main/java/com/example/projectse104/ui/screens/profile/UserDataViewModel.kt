package com.example.projectse104.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.domain.use_case.user.UpdateUserUseCase
import com.example.projectse104.domain.use_case.validation.ValidateEmail
import com.example.projectse104.domain.use_case.validation.ValidateFullName
import com.example.projectse104.domain.use_case.validation.ValidateLocation
import com.example.projectse104.domain.use_case.validation.ValidatePhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class   UserDataViewModel @Inject constructor(
    private val validateFullName: ValidateFullName,
    private val validateEmail: ValidateEmail,
    private val validatePhoneNumber: ValidatePhoneNumber,
    private val validateLocation: ValidateLocation,
    private val updateUserUseCase: UpdateUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId: String = savedStateHandle.get<String>("userId").toString()

    var state by mutableStateOf(UserDataFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

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

            UserDataFormEvent.Submit -> {
                submitData()
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
            println("Error in form submission")
            return
        }
        viewModelScope.launch {
            println("Sent data to update")
            val updateData = mapOf(
                "id" to userId,
                "fullName" to state.fullName,
                "email" to state.email,
                "phoneNumber" to state.phoneNumber,
                "location" to state.location
            )
            println("Update data: $updateData")
            updateUserUseCase(updateData).collect {
                when (it) {
                    is Response.Loading -> {
                        validationEventChannel.send(ValidationEvent.Loading)
                    }

                    is Response.Success -> {
                        validationEventChannel.send(ValidationEvent.Success)
                    }

                    is Response.Failure -> {
                        validationEventChannel.send(ValidationEvent.Error(it.e))
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