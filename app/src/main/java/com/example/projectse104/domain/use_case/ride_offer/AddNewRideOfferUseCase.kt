package com.example.projectse104.domain.use_case.ride_offer

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.repository.RideOfferRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddNewRideOfferUseCase @Inject constructor(
    private val rideRepository: RideOfferRepository
) {
    operator fun invoke(ride: RideOffer): Flow<Response<Unit>> = flow {
        emit(Response.Loading)
        try {
            rideRepository.addRideOffer(ride)
            emit(Response.Success(Unit))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}