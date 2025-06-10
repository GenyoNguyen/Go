package com.example.projectse104.domain.use_case.ride_offer

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.model.RideOfferWithLocation
import com.example.projectse104.domain.repository.LocationRepository
import com.example.projectse104.domain.repository.RideOfferRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOtherUserPendingOfferUseCase @Inject constructor(
    private val rideOfferRepository: RideOfferRepository,
    private val locationRepository: LocationRepository
) {
    operator fun invoke(userId: String, page: Int, limit: Int): Flow<Response<List<RideOfferWithLocation>>> =
        flow {
            emit(Response.Loading)
            val startTime = System.currentTimeMillis()
            when (val rideOfferListResponse = rideOfferRepository.getRideOfferListByOtherUser(
                userId, "PENDING", page, limit
            )) {
                is Response.Success<List<RideOffer>> -> {
                    println("Time to fetch ride offers: ${System.currentTimeMillis() - startTime}ms")
                    val rideOfferList = rideOfferListResponse.data ?: emptyList()
                    val locationStartTime = System.currentTimeMillis()
                    val locationIds = rideOfferList.flatMap { listOfNotNull(it.startLocationId, it.endLocationId) }.distinct()
                    val locationMap = when (val locationResponse = locationRepository.getLocationListByIds(locationIds)) {
                        is Response.Success -> locationResponse.data?.associateBy { it.id } ?: emptyMap()
                        else -> emptyMap()
                    }
                    println("Time to fetch locations: ${System.currentTimeMillis() - locationStartTime}ms")
                    val rideOfferWithLocationList = rideOfferList.map { ride ->
                        RideOfferWithLocation(
                            rideOffer = ride,
                            startLocation = locationMap[ride.startLocationId]?.name ?: "",
                            endLocation = locationMap[ride.endLocationId]?.name ?: ""
                        )
                    }
                    emit(Response.Success(rideOfferWithLocationList))
                }
                is Response.Failure -> {
                    emit(Response.Failure(Exception("Failed to get ride history")))
                }
                is Response.Loading -> {
                    emit(Response.Loading)
                }
                is Response.Idle -> {
                    emit(Response.Idle)
                }
            }
        }
}