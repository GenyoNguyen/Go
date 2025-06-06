package com.example.projectse104.ui.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.model.RideOfferWithLocation
import com.example.projectse104.domain.repository.UserResponse
import com.example.projectse104.domain.use_case.ride_offer.GetOtherUserPendingOfferUseCase
import com.example.projectse104.domain.use_case.user.GetUserUseCase
import com.example.projectse104.domain.use_case.user.LoadUserAva
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindARideVIewModel @Inject constructor(
    private val getOtherUserPendingOfferUseCase: GetOtherUserPendingOfferUseCase,
    private val loadUserAva: LoadUserAva,
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _rideOfferListState = MutableStateFlow<Response<List<RideOfferWithLocation>>>(Response.Idle)
    private val _isLoadingMore = MutableStateFlow(false)
    private val _user = MutableStateFlow<UserResponse>(Response.Loading)
    private val _avatarUrls = MutableStateFlow<Map<String, Response<String>>>(emptyMap()) // Lưu avatar theo userId của driver
    val rideOfferListState = _rideOfferListState.asStateFlow()
    val isLoadingMore = _isLoadingMore.asStateFlow()
    val user = _user.asStateFlow()
    val avatarUrls = _avatarUrls.asStateFlow()
    private var currentPage = 0
    private val pageSize = 4
    private var hasMoreData = true
    private val allRides = mutableListOf<RideOfferWithLocation>()

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            if (_rideOfferListState.value !is Response.Success) {
                getRideOfferList(userId, currentPage)
            }
            if (_user.value !is Response.Success) {
                getUser(userId)
            }
        }
    }

    fun getRideOfferList(userId: String, page: Int) {
        println("Loading view model... Page: $page")
        _isLoadingMore.value = true
        getOtherUserPendingOfferUseCase(userId, page, pageSize)
            .onEach { result ->
                when (result) {
                    is Response.Success -> {
                        val newRides = result.data ?: emptyList()
                        println("New rides: ${newRides.size}, Total allRides before: ${allRides.size}")
                        if (newRides.size < pageSize) {
                            hasMoreData = false
                        }
                        allRides.addAll(newRides.filter { newRide ->
                            !allRides.any { it.rideOffer.id == newRide.rideOffer.id }
                        })
                        // Tải avatar cho các driver mới
                        newRides.forEach { ride ->
                            if (!_avatarUrls.value.containsKey(ride.rideOffer.userId)) {
                                loadAvatarForDriver(ride.rideOffer.userId)
                            }
                        }
                        println("Total allRides after: ${allRides.size}")
                        _rideOfferListState.value = Response.Success(allRides.toList())
                        _isLoadingMore.value = false
                    }
                    is Response.Failure -> {
                        println("Error: ${result.e?.message}")
                        _rideOfferListState.value = result
                        _isLoadingMore.value = false
                    }
                    is Response.Loading -> {
                        println("State: Loading")
                    }
                    is Response.Idle -> {
                        println("State: Idle")
                        _rideOfferListState.value = Response.Idle
                        _isLoadingMore.value = false
                    }
                }
                println("State changed to: ${_rideOfferListState.value}")
            }
            .launchIn(viewModelScope)
    }

    private fun loadAvatarForDriver(driverId: String) {
        println("Loading avatar for driver: $driverId")
        viewModelScope.launch {
            val result = loadUserAva(driverId)
            println("Avatar result for $driverId: $result")
            _avatarUrls.value = _avatarUrls.value.toMutableMap().apply {
                put(driverId, result)
            }
        }
    }

    fun loadMoreRides(userId: String) {
        if (hasMoreData && !_isLoadingMore.value) {
            currentPage++
            getRideOfferList(userId, currentPage)
        }
    }

    fun hasMoreData(): Boolean = hasMoreData

    fun getUser(userId: String) {
        println("Loading user...")
        getUserUseCase(userId)
            .onEach { result ->
                _user.value = result
            }
            .launchIn(viewModelScope)
    }
}