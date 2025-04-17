package com.example.projectse104.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.repository.UserListResponse
import com.example.projectse104.domain.use_case.favourite_rider.GetRiderListFromUserFavouriteRiderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RideCircleViewModel @Inject constructor(
    private val getRiderListFromUserFavouriteRiderUseCase: GetRiderListFromUserFavouriteRiderUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _favouriteRiderListState = MutableStateFlow<UserListResponse>(Response.Loading)
    val favouriteRiderListState = _favouriteRiderListState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            getRiderListFromUserFavouriteRider(userId)
        }
    }

    private fun getRiderListFromUserFavouriteRider(userId: String) {
        getRiderListFromUserFavouriteRiderUseCase(userId)
            .onEach { result ->
                _favouriteRiderListState.value = result
            }
            .launchIn(viewModelScope)
    }

    fun disableLoading() {
        _isLoading.value = false
    }

    fun enableLoading() {
        _isLoading.value = true
    }
}