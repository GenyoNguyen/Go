package com.example.projectse104.domain.use_case.ride

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.repository.RideRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddNewRideUseCase @Inject constructor(
    private val rideRepository: RideRepository
) {
    operator fun invoke(ride: Ride): Flow<Response<Unit>> = flow {
        emit(Response.Loading)
        try {
            rideRepository.addRide(ride)
            emit(Response.Success(Unit))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}