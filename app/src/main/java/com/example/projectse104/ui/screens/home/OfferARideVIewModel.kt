package com.example.projectse104.ui.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.model.RideOfferWithLocation
import com.example.projectse104.domain.repository.UserResponse
import com.example.projectse104.domain.use_case.ride_offer.GetUserPendingOfferUseCase
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
class OfferARideVIewModel @Inject constructor(
    private val getUserPendingOfferUseCase: GetUserPendingOfferUseCase,
    private val loadUserAva: LoadUserAva,
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _rideOfferListState =
        MutableStateFlow<Response<List<RideOfferWithLocation>>>(Response.Idle)
    private val _isLoadingMore = MutableStateFlow(false)
    private val _user = MutableStateFlow<UserResponse>(Response.Loading)
    val rideOfferListState = _rideOfferListState.asStateFlow()
    val isLoadingMore = _isLoadingMore.asStateFlow()
    val user = _user.asStateFlow()
    private val _avatarUrl = MutableStateFlow<Response<String>>(Response.Idle)
    val avatarUrl = _avatarUrl.asStateFlow()

    private var currentPage = 0
    private val pageSize = 4
    private var hasMoreData = true
    private val allRideOffers = mutableListOf<RideOfferWithLocation>()

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            if (_rideOfferListState.value !is Response.Success) {
                getRideOfferList(userId, currentPage)
            }
            if (_user.value !is Response.Success) {
                getUser(userId)
                loadAvatar(userId)
            }
        }
    }

    fun getRideOfferList(userId: String, page: Int) {
        println("Loading view model... Page: $page")
        _isLoadingMore.value = true
        getUserPendingOfferUseCase(userId, page, pageSize)
            .onEach { result ->
                when (result) {
                    is Response.Success -> {
                        val newRideOffers = result.data ?: emptyList()
                        println("New ride offers: ${newRideOffers.size}, Total allRideOffers before: ${allRideOffers.size}")
                        if (newRideOffers.size < pageSize) {
                            hasMoreData = false
                        }
                        allRideOffers.addAll(newRideOffers.filter { newOffer ->
                            !allRideOffers.any { it.rideOffer.id == newOffer.rideOffer.id }
                        })
                        println("Total allRideOffers after: ${allRideOffers.size}")
                        _rideOfferListState.value = Response.Success(allRideOffers.toList())
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

    fun loadMoreRideOffers(userId: String) {
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

    private fun loadAvatar(userId: String) {
        println("Loading avatar...")
        viewModelScope.launch {
            val result = loadUserAva(userId)
            println("Avatar result: $result")
            _avatarUrl.value = result
        }
    }
}