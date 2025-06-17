package com.example.projectse104.domain.use_case.ride

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.RideRepository
import javax.inject.Inject

class UpdateRideRatingUseCase @Inject constructor(
    private val rideRepository: RideRepository
) {
    suspend operator fun invoke(rideId: String, rating: Float, comment: String): Response<Unit> {
        return rideRepository.updateRide(
            rideId,
            mapOf(
                "rating" to rating.toString(),
                "comment" to comment
            )
        )
    }
}
