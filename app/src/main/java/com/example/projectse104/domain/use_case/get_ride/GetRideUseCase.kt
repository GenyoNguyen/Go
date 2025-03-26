package com.example.projectse104.domain.use_case.get_ride

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.RideListResponse
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.RideResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRideUseCase @Inject constructor(
    private val repository: RideRepository
) {
    operator fun invoke(rideId: String): Flow<RideResponse> = flow {
        emit(Response.Loading)
        emit(repository.getRide(rideId))
    }
}