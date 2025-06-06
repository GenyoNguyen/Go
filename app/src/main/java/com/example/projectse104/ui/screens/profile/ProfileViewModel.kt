package com.example.projectse104.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.repository.UserResponse
import com.example.projectse104.domain.use_case.user.GetUserUseCase
import com.example.projectse104.domain.use_case.user.LoadUserAva
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val loadUserAvatar: LoadUserAva,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _userState = MutableStateFlow<UserResponse>(Response.Loading)
    val userState = _userState.asStateFlow()

    private val _avatarUrl = MutableStateFlow<Response<String>?>(null)
    val avatarUrl = _avatarUrl.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            fetchUserData(userId)
        }
    }

    fun fetchUserData(userId: String) {

            getUser(userId)
            loadAvatar(userId)

    }

    private fun getUser(userId: String) {
        println("Loading user data...")
        getUserUseCase(userId)
            .onEach { result ->
                _userState.value = result
                // Update isLoading based on both user and avatar states
                updateLoadingState()
            }
            .launchIn(viewModelScope)
    }

    private fun loadAvatar(userId: String) {
        println("Loading avatar...")
        viewModelScope.launch {
            val result = loadUserAvatar(userId)
            println("Avatar result: $result")
            _avatarUrl.value = result
            // Update isLoading based on both user and avatar states
            updateLoadingState()
        }
    }

    private fun updateLoadingState() {
        // Loading is false only when both user and avatar are not loading
        _isLoading.value = _userState.value is Response.Loading || _avatarUrl.value is Response.Loading
    }

    fun disableLoading() {
        _isLoading.value = false
    }
}