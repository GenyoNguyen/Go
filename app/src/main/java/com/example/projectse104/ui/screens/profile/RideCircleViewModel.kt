package com.example.projectse104.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.repository.UserListResponse
import com.example.projectse104.domain.use_case.favourite_rider.GetRiderListFromUserFavouriteRiderUseCase
import com.example.projectse104.domain.use_case.ride.GetRideStatisticsUseCase
import com.example.projectse104.domain.use_case.ride.RideStatistics
import com.example.projectse104.domain.use_case.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RideCircleViewModel @Inject constructor(
    private val getRiderListFromUserFavouriteRiderUseCase: GetRiderListFromUserFavouriteRiderUseCase,
    private val getRideStatisticsUseCase: GetRideStatisticsUseCase,
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _favouriteRiderListState = MutableStateFlow<UserListResponse>(Response.Loading)
    val favouriteRiderListState = _favouriteRiderListState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _rideStatisticsState = MutableStateFlow<Response<RideStatistics>>(Response.Loading)
    val rideStatisticsState: StateFlow<Response<RideStatistics>> = _rideStatisticsState.asStateFlow()

    private val _userState = MutableStateFlow<Response<User>>(Response.Loading)
    val userState: StateFlow<Response<User>> = _userState.asStateFlow()

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            getRiderListFromUserFavouriteRider(userId)
            getRideStatistics(userId)
            getUser(userId)
        }
    }

    private fun getRiderListFromUserFavouriteRider(userId: String) {
        getRiderListFromUserFavouriteRiderUseCase(userId)
            .onEach { result ->
                _favouriteRiderListState.value = result
            }
            .launchIn(viewModelScope)
    }

    private fun getRideStatistics(userId: String) {
        viewModelScope.launch {
            _rideStatisticsState.value = Response.Loading
            val result = getRideStatisticsUseCase(userId)
            _rideStatisticsState.value = result
        }
    }

    private fun getUser(userId: String) {
        viewModelScope.launch {
            getUserUseCase(userId).collect { result ->
                _userState.value = result as? Response<User> ?: Response.Failure(Exception("Invalid user response"))
            }
        }
    }

    fun disableLoading() {
        _isLoading.value = false
    }

    fun enableLoading() {
        _isLoading.value = true
    }
}