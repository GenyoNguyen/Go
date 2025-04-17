package com.example.projectse104.domain.use_case.ride_offer

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.RideOfferResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRideOfferUseCase @Inject constructor(
    private val repository: RideOfferRepository
) {
    operator fun invoke(rideOfferId: String): Flow<RideOfferResponse> = flow {
        emit(Response.Loading)
        emit(repository.getRideOffer(rideOfferId))
    }
}