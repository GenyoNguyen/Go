package com.example.projectse104.ui.screens.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.model.RideWithRideOfferWithLocation
import com.example.projectse104.domain.use_case.ride.GetRideHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getRideHistoryUseCase: GetRideHistoryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _rideListState =
        MutableStateFlow<Response<List<RideWithRideOfferWithLocation>>>(Response.Loading)
    val rideListState = _rideListState.asStateFlow()

    init {
        if (_rideListState.value !is Response.Success) {
            savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
                getRideHistoryList(userId)
            }
        }
    }

    fun getRideHistoryList(userId: String) {
        println("Loading view model...")
        getRideHistoryUseCase(userId)
            .onEach { result ->
                _rideListState.value = result
            }
            .launchIn(viewModelScope)
    }
}