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
import com.example.projectse104.domain.use_case.ride_offer.*
@HiltViewModel
class OfferARideVIewModel @Inject constructor(
    private val getUserPendingOfferUseCase: GetUserPendingOfferUseCase,
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _rideOfferListState =
        MutableStateFlow<Response<List<RideOfferWithLocation>>>(Response.Loading)
    private val _user =
        MutableStateFlow<UserResponse>(Response.Loading)
    val rideOfferListState = _rideOfferListState.asStateFlow()
    val user=_user.asStateFlow()
    init {
        if (_rideOfferListState.value !is Response.Success) {
            savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
                getRideOfferList(userId)
            }
        }
        if (_user.value !is Response.Success) {
            savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
                getUser(userId)
            }
        }
    }

    fun getRideOfferList(userId: String) {
        println("Loading view model...")
        getUserPendingOfferUseCase(userId)
            .onEach { result ->
                _rideOfferListState.value = result
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