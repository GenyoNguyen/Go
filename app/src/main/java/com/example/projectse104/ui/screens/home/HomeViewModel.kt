package com.example.projectse104.ui.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.model.*
import com.example.projectse104.domain.repository.UserResponse
import com.example.projectse104.domain.use_case.ride.GetRidesHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.example.projectse104.domain.use_case.user.*

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRidesHomeUseCase: GetRidesHomeUseCase,
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _rideListState =
        MutableStateFlow<Response<List<RideWithRideOfferWithLocation>>>(Response.Loading)
    private val _user =
        MutableStateFlow<UserResponse>(Response.Loading)
    val rideListState = _rideListState.asStateFlow()
    val user=_user.asStateFlow()
    init {
        if (_rideListState.value !is Response.Success) {
            savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
                getRideList(userId)
            }
        }
        if (_user.value !is Response.Success) {
            savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
                getUser(userId)
            }
        }
    }

    fun getRideList(userId: String) {
        println("Loading view model...")
        getRidesHomeUseCase(userId)
            .onEach { result ->
                _rideListState.value = result
            }
            .launchIn(viewModelScope)
    }
    fun getUser(userId: String) {
        println("Loading view model...")
        getUserUseCase(userId)
            .onEach { result ->
                _user.value = result
            }
            .launchIn(viewModelScope)
    }
}