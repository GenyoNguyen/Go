package com.example.projectse104.ui.screens.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.model.RideWithRideOfferWithLocation
import com.example.projectse104.domain.use_case.ride.GetRideHistoryUseCase
import com.example.projectse104.domain.use_case.user.LoadUserAva
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val loadUserAva: LoadUserAva,
    private val getRideHistoryUseCase: GetRideHistoryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _rideListState = MutableStateFlow<Response<List<RideWithRideOfferWithLocation>>>(Response.Idle)
    private val _isLoadingMore = MutableStateFlow(false)
    private val _avatarUrls = MutableStateFlow<Map<String, Response<String>>>(emptyMap()) // Lưu avatar theo userId của tài xế
    val rideListState = _rideListState.asStateFlow()
    val isLoadingMore = _isLoadingMore.asStateFlow()
    val avatarUrls = _avatarUrls.asStateFlow()

    private var currentPage = 0
    private val pageSize = 3
    private var hasMoreData = true
    private val allRides = mutableListOf<RideWithRideOfferWithLocation>()
    private val loadingAvatarIds = mutableSetOf<String>() // Ngăn tải trùng lặp

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            if (_rideListState.value !is Response.Success) {
                getRideHistoryList(userId, currentPage)
            }
        }
    }

    fun getRideHistoryList(userId: String, page: Int) {
        println("Loading view model... Page: $page")
        _isLoadingMore.value = true
        getRideHistoryUseCase(userId, page, pageSize)
            .onEach { result ->
                when (result) {
                    is Response.Success -> {
                        val newRides = result.data ?: emptyList()
                        println("New rides: ${newRides.size}, Total allRides before: ${allRides.size}")
                        if (newRides.size < pageSize) {
                            hasMoreData = false
                        }
                        allRides.addAll(newRides.filter { newRide ->
                            !allRides.any { it.ride.id == newRide.ride.id }
                        })
                        // Tải avatar cho các tài xế mới
                        newRides.forEach { ride ->
                            val driverId = ride.rideOffer.userId
                            if (!_avatarUrls.value.containsKey(driverId)) {
                                loadAvatarForDriver(driverId)
                            }
                        }
                        println("Total allRides after: ${allRides.size}")
                        _rideListState.value = Response.Success(allRides.toList())
                        _isLoadingMore.value = false
                    }
                    is Response.Failure -> {
                        println("Error: ${result.e?.message}")
                        _rideListState.value = result
                        _isLoadingMore.value = false
                    }
                    is Response.Loading -> {
                        println("State: Loading")
                    }
                    is Response.Idle -> {
                        println("State: Idle")
                        _rideListState.value = Response.Idle
                        _isLoadingMore.value = false
                    }
                }
                println("State changed to: ${_rideListState.value}")
            }
            .launchIn(viewModelScope)
    }

    private fun loadAvatarForDriver(driverId: String) {
        if (loadingAvatarIds.contains(driverId)) return
        loadingAvatarIds.add(driverId)
        println("Loading avatar for driver: $driverId")
        viewModelScope.launch {
            val result = loadUserAva(driverId)
            println("Avatar result for $driverId: $result")
            _avatarUrls.value = _avatarUrls.value.toMutableMap().apply {
                put(driverId, result)
            }
            loadingAvatarIds.remove(driverId)
        }
    }

    fun loadMoreRides(userId: String) {
        if (hasMoreData && !_isLoadingMore.value) {
            currentPage++
            getRideHistoryList(userId, currentPage)
        }
    }

    fun hasMoreData(): Boolean = hasMoreData
}