package com.example.projectse104.domain.use_case.ride_offer

import com.example.projectse104.domain.repository.RideOfferRepository
import javax.inject.Inject

class DeleteRideOfferUseCase @Inject constructor(
    private val rideOfferRepository: RideOfferRepository
) {
    suspend operator fun invoke(id: String) {
        rideOfferRepository.deleteRideOffer(id)
    }
}
