package com.example.projectse104.ui.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.RIDE_NUMBER_FIELD
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.model.RideOfferWithLocation
import com.example.projectse104.domain.model.RideOfferWithLocationRider
import com.example.projectse104.domain.model.RideWithUserWithLocation
import com.example.projectse104.domain.use_case.ride.GetRideDetailsHistoryUseCase
import com.example.projectse104.domain.use_case.ride_offer.GetRideOfferUseCase
import com.example.projectse104.domain.use_case.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.example.projectse104.domain.repository.UserResponse

@HiltViewModel
class OfferDetailsViewModel @Inject constructor(
    private val getRideOfferDetails: GetRideOfferUseCase,
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _offerState =
        MutableStateFlow<Response<RideOfferWithLocationRider>>(Response.Loading)
    private val _user =
        MutableStateFlow<UserResponse>(Response.Loading)
    val offerState = _offerState.asStateFlow()
    val user=_user.asStateFlow()
    init {
        if (_offerState.value !is Response.Success) {
            savedStateHandle.get<String>(RIDE_NUMBER_FIELD)?.let { offerId ->
                getRideDetails(offerId)
            }
        }
        if (_user.value !is Response.Success) {
            savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
                getUser(userId)
            }
        }
    }

    fun getRideDetails(offerId: String) {
        println("Loading ride details view model...")
        getRideOfferDetails(offerId)
            .onEach { result ->
                _offerState.value = result
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