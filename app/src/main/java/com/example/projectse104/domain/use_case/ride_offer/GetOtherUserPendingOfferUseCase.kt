package com.example.projectse104.domain.use_case.ride_offer

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.model.RideOfferWithLocation
import com.example.projectse104.domain.model.RideWithRideOfferWithLocation
import com.example.projectse104.domain.repository.LocationRepository
import com.example.projectse104.domain.repository.RideOfferRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOtherUserPendingOfferUseCase @Inject constructor(
    private val rideOfferRepository: RideOfferRepository,
    private val locationRepository: LocationRepository
) {
    operator fun invoke(userId: String): Flow<Response<List<RideOfferWithLocation>>> =
        flow {
            emit(Response.Loading)
            when (val rideOfferListResponse = rideOfferRepository.getRideOfferListByOtherUser(userId,"PENDING")) {
                is Response.Success<List<RideOffer>> -> {
                    print("response is success")
                    val rideOfferList = rideOfferListResponse.data
                    val rideOfferWithLocationList = mutableListOf<RideOfferWithLocation>()
                    rideOfferList?.forEach { ride ->
                        val startLocation = ride.startLocationId?.let { locationId ->
                            when (val locationResponse = locationRepository.getLocation(locationId)) {
                                is Response.Success -> locationResponse.data?.name ?: ""
                                else -> ""
                            }
                        } ?: ""

                        val endLocation = ride.endLocationId?.let { locationId ->
                            when (val locationResponse = locationRepository.getLocation(locationId)) {
                                is Response.Success -> locationResponse.data?.name ?: ""
                                else -> ""
                            }
                        } ?: ""

                        rideOfferWithLocationList.add(
                            RideOfferWithLocation(
                                rideOffer = ride,
                                startLocation = startLocation,
                                endLocation = endLocation,
                            )
                        )
                    }
                    emit(Response.Success(rideOfferWithLocationList))

                }
                else -> emit(Response.Failure(Exception("Failed to get ride history")))
            }
        }
}