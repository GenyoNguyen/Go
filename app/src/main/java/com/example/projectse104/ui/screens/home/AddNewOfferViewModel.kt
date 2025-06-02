package com.example.projectse104.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Location
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.use_case.location.GetLocationListUseCase
import com.example.projectse104.domain.use_case.ride_offer.AddNewRideOfferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewOfferViewModel @Inject constructor(
    private val getLocationListUseCase: GetLocationListUseCase,
    private val addNewRideOfferUseCase: AddNewRideOfferUseCase
) : ViewModel() {

    private val _locationListState = MutableStateFlow<Response<List<Location>>?>(null)
    val locationListState = _locationListState.asStateFlow()

    private val _addRideOfferState = MutableStateFlow<Response<Unit>?>(null)
    val addRideOfferState = _addRideOfferState.asStateFlow()

    init {
        fetchLocationList()
    }

    private fun fetchLocationList() {
        viewModelScope.launch {
            getLocationListUseCase().collect { response ->
                _locationListState.value = response
            }
        }
    }

    fun addRideOffer(rideOffer: RideOffer) {
        viewModelScope.launch {
            addNewRideOfferUseCase(rideOffer).collect { response ->
                _addRideOfferState.value = response
            }
        }
    }
}