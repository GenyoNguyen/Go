package com.example.projectse104.domain.use_case.ride

import com.example.projectse104.core.Response
import com.example.projectse104.core.RideStatus
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.repository.RideListResponse
import com.example.projectse104.domain.repository.RideRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPendingRideListUseCase @Inject constructor(
    private val repository: RideRepository
) {
    operator fun invoke(passengerId: String): Flow<RideListResponse> = flow {
        emit(Response.Loading)
        val rideListFlow = repository.getRideListFlowGivenPassenger(passengerId)
        rideListFlow.collect { it ->
            when (it) {
                is Response.Success<List<Ride>> -> {
                    val filteredRideList = it.data?.filter {
                        it.status == RideStatus.PENDING
                    }
                    emit(Response.Success(filteredRideList))
                }

                else -> emit(it)
            }
        }
    }
}