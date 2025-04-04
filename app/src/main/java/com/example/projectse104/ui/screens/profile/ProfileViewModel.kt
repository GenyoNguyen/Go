package com.example.projectse104.ui.screens.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.repository.UserResponse
import com.example.projectse104.domain.use_case.get_user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _userState = mutableStateOf<UserResponse>(Response.Loading)
    val userState: State<UserResponse> = _userState

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            getUser(userId)
        }
    }

    private fun getUser(userId: String) {
        println("Loading view model...")
        getUserUseCase(userId)
            .onEach { result -> _userState.value = result }
            .launchIn(viewModelScope)
    }
}