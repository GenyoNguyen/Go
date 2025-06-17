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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FindARideViewModel @Inject constructor(
    private val getOtherUserPendingOfferUseCase: GetOtherUserPendingOfferUseCase,
    private val loadUserAva: LoadUserAva,
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _rideOfferListState = MutableStateFlow<Response<List<RideOfferWithLocation>>>(Response.Idle)
    private val _filteredRides = MutableStateFlow<List<RideOfferWithLocation>>(emptyList())
    private val _isLoadingMore = MutableStateFlow(false)
    private val _user = MutableStateFlow<UserResponse>(Response.Loading)
    private val _avatarUrls = MutableStateFlow<Map<String, Response<String>>>(emptyMap())
    val rideOfferListState = _rideOfferListState.asStateFlow()
    val filteredRides = _filteredRides.asStateFlow()
    val isLoadingMore = _isLoadingMore.asStateFlow()
    val user = _user.asStateFlow()
    val avatarUrls = _avatarUrls.asStateFlow()
    private var currentPage = 0
    private val pageSize = 10
    private var hasMoreData = true
    private val allRides = mutableListOf<RideOfferWithLocation>()
    private val rideIds = mutableSetOf<String>()

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
        _isLoadingMore.value = true
        viewModelScope.launch(Dispatchers.IO) { // Chuyển sang IO
            getOtherUserPendingOfferUseCase(userId, page, pageSize)
                .onEach { result ->
                    when (result) {
                        is Response.Success -> {
                            val newRides = result.data ?: emptyList()
                            println("New rides: ${newRides.size}, Total allRides before: ${allRides.size}")
                            if (newRides.size < pageSize) {
                                hasMoreData = false
                            }
                            allRides.addAll(newRides.filter { rideIds.add(it.rideOffer.id) })
                            val newDriverIds = newRides.map { it.rideOffer.userId }
                                .filter { !_avatarUrls.value.containsKey(it) }
                                .distinct()
                            if (newDriverIds.isNotEmpty()) {
                                newDriverIds.forEach { driverId ->
                                    loadAvatarForDriver(driverId)
                                }
                            }
                            println("Total allRides after: ${allRides.size}")
                            withContext(Dispatchers.Main) { // Cập nhật UI trên main thread
                                _rideOfferListState.value = Response.Success(allRides.toList())
                                _filteredRides.value = allRides
                                _isLoadingMore.value = false
                            }
                        }
                        is Response.Failure -> {
                            println("Error: ${result.e?.message}")
                            withContext(Dispatchers.Main) {
                                _rideOfferListState.value = result
                                _isLoadingMore.value = false
                            }
                        }
                        is Response.Loading -> println("State: Loading")
                        is Response.Idle -> {
                            println("State: Idle")
                            withContext(Dispatchers.Main) {
                                _rideOfferListState.value = Response.Idle
                                _isLoadingMore.value = false
                            }
                        }
                    }
                    println("State changed to: ${_rideOfferListState.value}")
                }
                .launchIn(this)
        }
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

    fun filterRides(searchQuery: String) {
        val query = searchQuery.trim().lowercase()
        _filteredRides.value = allRides.filter {
            it.startLocation.lowercase().contains(query) || it.endLocation.lowercase().contains(query)
        }
    }
}