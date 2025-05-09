package com.example.projectse104.ui.screens.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.RIDE_NUMBER_FIELD
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideWithPassengerAndRider
import com.example.projectse104.domain.use_case.ride.GetRideDetailsRatingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RideDetailsRatingViewModel @Inject constructor(
    private val getRideDetailsRatingUseCase: GetRideDetailsRatingUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _rideState =
        MutableStateFlow<Response<RideWithPassengerAndRider>>(Response.Loading)
    val rideState = _rideState.asStateFlow()

    init {
        if (_rideState.value !is Response.Success) {
            savedStateHandle.get<String>(RIDE_NUMBER_FIELD)?.let { rideId ->
                getRideDetailsRating(rideId)
            }
        }
    }

    fun getRideDetailsRating(rideId: String) {
        println("Loading ride details history view model...")
        getRideDetailsRatingUseCase(rideId)
            .onEach { result ->
                _rideState.value = result
            }
            .launchIn(viewModelScope)
    }
}