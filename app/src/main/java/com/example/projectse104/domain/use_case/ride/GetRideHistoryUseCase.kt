package com.example.projectse104.domain.use_case.ride

import com.example.projectse104.core.Response
import com.example.projectse104.core.RideStatus
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.RideWithRideOfferWithLocation
import com.example.projectse104.domain.repository.LocationRepository
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.RideRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRideHistoryUseCase @Inject constructor(
    private val rideRepository: RideRepository,
    private val rideOfferRepository: RideOfferRepository,
    private val locationRepository: LocationRepository
) {
    operator fun invoke(passengerId: String): Flow<Response<List<RideWithRideOfferWithLocation>>> =
        flow {
            emit(Response.Loading)
            when (val rideListResponse = rideRepository.getRideListGivenPassenger(passengerId)) {
                is Response.Success<List<Ride>> -> {
                    val filteredRideList = rideListResponse.data?.filter {
                        it.status == RideStatus.SUCCESS
                    }

                    val ridesWithLocations = mutableListOf<RideWithRideOfferWithLocation>()

                    filteredRideList?.forEach { ride ->
                        val rideOffer = ride.rideOfferId?.let { id ->
                            when (val rideOfferResponse = rideOfferRepository.getRideOffer
                                (rideOfferId = id)) {
                                is Response.Success -> rideOfferResponse.data
                                    ?: throw Exception("RideOffer data is null")

                                else -> throw Exception("Cannot find RideOffer for this ride.")
                            }
                        } ?: throw Exception("Cannot find RideOffer for this ride")

                        val startLocation = rideOffer.startLocationId?.let { locationId ->
                            when (val locationResponse =
                                locationRepository.getLocation(locationId)) {
                                is Response.Success -> locationResponse.data?.name ?: ""
                                else -> ""
                            }
                        } ?: ""

                        val endLocation = rideOffer.endLocationId?.let { locationId ->
                            when (val locationResponse =
                                locationRepository.getLocation(locationId)) {
                                is Response.Success -> locationResponse.data?.name ?: ""
                                else -> ""
                            }
                        } ?: ""

                        ridesWithLocations.add(
                            RideWithRideOfferWithLocation(
                                ride = ride,
                                rideOffer = rideOffer,
                                startLocation = startLocation,
                                endLocation = endLocation,
                            )
                        )
                    }

                    emit(Response.Success(ridesWithLocations))
                }

                else -> emit(Response.Failure(Exception("Failed to get ride history")))
            }
        }
}