package com.example.projectse104.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Location
import com.example.projectse104.domain.model.UserLocation
import com.example.projectse104.domain.use_case.location.GetLocationListUseCase
import com.example.projectse104.domain.use_case.user.AddUserLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewAddressViewModel @Inject constructor(
    private val getLocationListUseCase: GetLocationListUseCase,
    private val addUserLocationUseCase: AddUserLocationUseCase
) : ViewModel() {
    private val _locationListState = MutableStateFlow<Response<List<Location>>>(Response.Loading)
    var locationListState: StateFlow<Response<List<Location>>> = _locationListState.asStateFlow()

    private val _addUserLocationState = MutableStateFlow<Response<Unit>>(Response.Loading)
    var addUserLocationState: StateFlow<Response<Unit>> = _addUserLocationState.asStateFlow()

    fun fetchLocationList() {
        viewModelScope.launch {
            getLocationListUseCase().collect { result ->
                _locationListState.value = result
            }
        }
    }

    fun addUserLocation(userLocation: UserLocation) {
        viewModelScope.launch {
            addUserLocationUseCase(userLocation).collect { result ->
                _addUserLocationState.value = result
            }
        }
    }
}

