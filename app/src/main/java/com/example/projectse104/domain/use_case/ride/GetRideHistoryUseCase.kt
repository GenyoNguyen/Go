package com.example.projectse104.domain.use_case.ride

import com.example.projectse104.core.Response
import com.example.projectse104.core.RideStatus
import com.example.projectse104.domain.model.RideWithRideOffer
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.RideWithRideOfferListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRideHistoryUseCase @Inject constructor(
    private val repository: RideRepository
) {
    operator fun invoke(passengerId: String): Flow<RideWithRideOfferListResponse> = flow {
        emit(Response.Loading)
        when (val rideListResponse = repository.getRideListGivenPassenger(passengerId)) {
            is Response.Success<List<RideWithRideOffer>> -> {
                val filteredRideList = rideListResponse.data?.filter {
                    it.status == RideStatus.SUCCESS
                }
                emit(Response.Success(filteredRideList))
            }

            else -> emit(rideListResponse)
        }
    }
}