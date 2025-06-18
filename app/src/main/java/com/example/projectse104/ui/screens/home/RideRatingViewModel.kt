package com.example.projectse104.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.domain.repository.RideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RideRatingViewModel @Inject constructor(
    private val rideRepository: RideRepository
) : ViewModel() {

    fun submitRating(rideId: String, rating: Float, comment: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            rideRepository.updateRide(
                rideId,
                mapOf(
                    "rating" to rating.toString(),
                    "comment" to comment
                )
            )
            onSuccess()
        }
    }
}
