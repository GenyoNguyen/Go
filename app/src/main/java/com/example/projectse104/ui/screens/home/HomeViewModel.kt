package com.example.projectse104.ui.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.model.RideWithRideOfferWithLocation
import com.example.projectse104.domain.repository.UserResponse
import com.example.projectse104.domain.use_case.ride.GetRidesHomeUseCase
import com.example.projectse104.domain.use_case.ride.TriggerRideUseCase
import com.example.projectse104.domain.use_case.user.GetUserUseCase
import com.example.projectse104.domain.use_case.user.LoadUserAva
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRidesHomeUseCase: GetRidesHomeUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val loadUserAva: LoadUserAva,
    private val triggerRideUseCase: TriggerRideUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _rideListState =
        MutableStateFlow<Response<List<RideWithRideOfferWithLocation>>>(Response.Idle)
    private val _isLoadingMore = MutableStateFlow(false)
    private val _user = MutableStateFlow<UserResponse>(Response.Loading)
    private val _avatarUrls = MutableStateFlow<Map<String, Response<String>>>(emptyMap())
    val rideListState = _rideListState.asStateFlow()
    val isLoadingMore = _isLoadingMore.asStateFlow()
    val user = _user.asStateFlow()
    val avatarUrls = _avatarUrls.asStateFlow()

    private var currentPage = 0
    private val pageSize = 10 // Tăng từ 4 lên 10
    private var hasMoreData = true
    private val allRides = mutableListOf<RideWithRideOfferWithLocation>()
    private val rideIds = mutableSetOf<String>()

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            if (_rideListState.value !is Response.Success) {
                getRideList(userId, currentPage)
            }
            if (_user.value !is Response.Success) {
                getUser(userId)
            }
        }
    }

    fun getRideList(userId: String, page: Int) {
        println("Loading view model... Page: $page")
        _isLoadingMore.value = true
        viewModelScope.launch(Dispatchers.IO) {
            getRidesHomeUseCase(userId, page, pageSize)
                .onEach { result ->
                    when (result) {
                        is Response.Success -> {
                            val newRides = result.data ?: emptyList()
                            println("New rides: ${newRides.size}, Total allRides before: ${allRides.size}")
                            if (newRides.size < pageSize) {
                                hasMoreData = false
                            }
                            allRides.addAll(newRides.filter { rideIds.add(it.ride.id) })
                            newRides.forEach { ride ->
                                if (!_avatarUrls.value.containsKey(ride.rideOffer.userId)) {
                                    loadAvatarForDriver(ride.rideOffer.userId)
                                }
                            }
                            println("Total allRides after: ${allRides.size}")
                            withContext(Dispatchers.Main) {
                                _rideListState.value = Response.Success(allRides.toList())
                                _isLoadingMore.value = false
                            }
                        }

                        is Response.Failure -> {
                            println("Error: ${result.e?.message}")
                            withContext(Dispatchers.Main) {
                                _rideListState.value = result
                                _isLoadingMore.value = false
                            }
                        }

                        is Response.Loading -> println("State: Loading")
                        is Response.Idle -> {
                            println("State: Idle")
                            withContext(Dispatchers.Main) {
                                _rideListState.value = Response.Idle
                                _isLoadingMore.value = false
                            }
                        }
                    }
                    println("State changed to: ${_rideListState.value}")
                }
                .launchIn(this)
        }
    }

    private fun loadAvatarForDriver(driverId: String) {
        println("Loading avatar for driver: $driverId")
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadUserAva(driverId)
            println("Avatar result for $driverId: $result")
            withContext(Dispatchers.Main) {
                _avatarUrls.value = _avatarUrls.value.toMutableMap().apply {
                    put(driverId, result)
                }
            }
        }
    }

    fun startRide(rideId: String) {
        viewModelScope.launch {
            triggerRideUseCase(rideId, true)
        }
    }

    fun endRide(rideId: String) {
        viewModelScope.launch {
            triggerRideUseCase(rideId, false)
        }
    }

    fun loadMoreRides(userId: String) {
        if (hasMoreData && !_isLoadingMore.value) {
            currentPage++
            getRideList(userId, currentPage)
        }
    }

    fun hasMoreData(): Boolean = hasMoreData

    fun getUser(userId: String) {
        println("Loading user...")
        viewModelScope.launch(Dispatchers.IO) {
            getUserUseCase(userId)
                .onEach { result ->
                    withContext(Dispatchers.Main) {
                        _user.value = result
                    }
                }
                .launchIn(this)
        }
    }
}