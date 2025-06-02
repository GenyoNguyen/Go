package com.example.projectse104.domain.use_case.ride_offer

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.model.RideOfferWithLocation
import com.example.projectse104.domain.model.RideOfferWithLocationRider
import com.example.projectse104.domain.repository.LocationRepository
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.RideOfferResponse
import com.example.projectse104.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRideOfferUseCase @Inject constructor(
    private val rideOfferRepository: RideOfferRepository,
    private val locationRepository: LocationRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(rideId: String): Flow<Response<RideOfferWithLocationRider>> =
        flow {
            emit(Response.Loading)
            when (val rideOfferResponse = rideOfferRepository.getRideOffer(rideId)) {
                is Response.Success<RideOffer> -> {
                    val rideOffer = rideOfferResponse.data
                    if (rideOffer != null) {
                        val startLocation = rideOffer.startLocationId?.let { locationId ->
                            when (val locationResponse = locationRepository.getLocation(locationId)) {
                                is Response.Success -> locationResponse.data?.name ?: ""
                                else -> ""
                            }
                        } ?: ""

                        val endLocation = rideOffer.endLocationId?.let { locationId ->
                            when (val locationResponse = locationRepository.getLocation(locationId)) {
                                is Response.Success -> locationResponse.data?.name ?: ""
                                else -> ""
                            }
                        } ?: ""
                        val rider = rideOffer.userId?.let { riderId ->
                            when (val userResponse = userRepository.getUser(riderId)) {
                                is Response.Success -> userResponse.data
                                    ?: throw Exception("User data is null")

                                else -> throw Exception("Cannot find User for this ride.")
                            }
                        } ?: throw Exception("Cannot find Rider for this ride")

                        emit(Response.Success(
                            RideOfferWithLocationRider(
                                rideOffer = rideOffer,
                                startLocation = startLocation,
                                endLocation = endLocation,
                                rider = rider
                            )
                        ))
                    } else {
                        emit(Response.Failure(Exception("Ride offer data is null")))
                    }
                }
                else -> emit(Response.Failure(Exception("Failed to get ride history")))
            }
        }
}