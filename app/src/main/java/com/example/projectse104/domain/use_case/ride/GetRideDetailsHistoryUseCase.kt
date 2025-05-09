package com.example.projectse104.domain.use_case.ride

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.RideWithUserWithLocation
import com.example.projectse104.domain.repository.LocationRepository
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRideDetailsHistoryUseCase @Inject constructor(
    private val rideRepository: RideRepository,
    private val rideOfferRepository: RideOfferRepository,
    private val locationRepository: LocationRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(rideId: String): Flow<Response<RideWithUserWithLocation>> =
        flow {
            emit(Response.Loading)
            when (val rideListResponse = rideRepository.getRide(rideId)) {
                is Response.Success<Ride> -> {
                    val ride = rideListResponse.data!!

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

                    val rider = rideOffer.userId?.let { riderId ->
                        when (val userResponse = userRepository.getUser(riderId)) {
                            is Response.Success -> userResponse.data
                                ?: throw Exception("User data is null")

                            else -> throw Exception("Cannot find User for this ride.")
                        }
                    } ?: throw Exception("Cannot find Rider for this ride")

                    val passenger = ride.passengerId?.let { passengerId ->
                        when (val userResponse = userRepository.getUser(passengerId)) {
                            is Response.Success -> userResponse.data
                                ?: throw Exception("User data is null")

                            else -> throw Exception("Cannot find User for this ride.")
                        }
                    } ?: throw Exception("Cannot find Passenger for this ride")

                    emit(
                        Response.Success(
                            RideWithUserWithLocation(
                                ride = ride,
                                rideOffer = rideOffer,
                                startLocation = startLocation,
                                endLocation = endLocation,
                                rider = rider,
                                passenger = passenger
                            )
                        )
                    )
                }

                else -> emit(Response.Failure(Exception("Failed to get ride history details")))
            }
        }
}
