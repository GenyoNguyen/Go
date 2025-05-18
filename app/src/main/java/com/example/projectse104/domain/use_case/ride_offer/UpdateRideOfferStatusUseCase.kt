package com.example.projectse104.domain.use_case.ride_offer

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.RideOfferRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateRideOfferStatusUseCase @Inject constructor(
    private val rideOfferRepository: RideOfferRepository
) {
    operator fun invoke(rideOfferId: String, status: String): Flow<Response<Unit>> = flow {
        emit(Response.Loading)
        try {
            val result = rideOfferRepository.updateRideOfferStatus(rideOfferId, status)
            when (result) {
                is Response.Success -> emit(Response.Success(Unit))
                is Response.Failure -> emit(Response.Failure(result.e))
                else -> emit(Response.Failure(Exception("Unexpected result state")))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}