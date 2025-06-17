package com.example.projectse104.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.use_case.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotionRewardsViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _userState = MutableStateFlow<Response<User>>(Response.Loading)
    val userState: StateFlow<Response<User>> = _userState.asStateFlow()


    init {
        val userId = savedStateHandle.get<String>(USER_ID_FIELD)
        userId?.let {
            fetchUser(it)
        }
    }

    private fun fetchUser(userId: String) {
        viewModelScope.launch {
            getUserUseCase(userId).collect { result ->
                _userState.value = result as? Response<User> ?: Response.Failure(Exception("Invalid user response"))
            }
        }
    }
}