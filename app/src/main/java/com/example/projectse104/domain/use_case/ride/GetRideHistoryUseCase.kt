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
    operator fun invoke(passengerId: String, page: Int, limit: Int): Flow<Response<List<RideWithRideOfferWithLocation>>> =
        flow {
            emit(Response.Loading)
            val from = (page * limit).toLong()
            val to = (from + limit +1).toLong()
            println("Fetching ride history for passengerId: $passengerId, from: $from, to: $to")
            when (val rideListResponse = rideRepository.getRideListGivenPassengerPaginated(passengerId, from, to)) {
                is Response.Success<List<Ride>> -> {
                    println("Fetched ${rideListResponse.data?.size} rides")
                    val filteredRideList = rideListResponse.data?.filter {
                        it.status == RideStatus.SUCCESS || it.status==RideStatus.CANCELLED
                    } ?: emptyList()
                    println("Filtered ${filteredRideList.size} rides with status SUCCESS")
                    val ridesWithLocations = mutableListOf<RideWithRideOfferWithLocation>()
                    filteredRideList.forEach { ride ->
                        try {
                            val rideOffer = ride.rideOfferId?.let { id ->
                                when (val rideOfferResponse = rideOfferRepository.getRideOffer(id)) {
                                    is Response.Success -> rideOfferResponse.data
                                        ?: throw Exception("RideOffer data is null")
                                    is Response.Failure -> throw Exception("Cannot find RideOffer for ride ${ride.id}: ${rideOfferResponse.e?.message}")
                                    else -> throw Exception("Cannot find RideOffer for ride ${ride.id}")
                                }
                            } ?: throw Exception("Cannot find RideOffer for ride ${ride.id}")
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
                            ridesWithLocations.add(
                                RideWithRideOfferWithLocation(
                                    ride = ride,
                                    rideOffer = rideOffer,
                                    startLocation = startLocation,
                                    endLocation = endLocation
                                )
                            )
                        } catch (e: Exception) {
                            println("Error processing ride ${ride.id}: ${e.message}")
                            // Bỏ qua chuyến đi này và tiếp tục với chuyến tiếp theo
                        }
                    }
                    println("Emitting ${ridesWithLocations.size} rides with locations")
                    emit(Response.Success(ridesWithLocations))
                }
                is Response.Failure -> {
                    println("Failed to get ride history: ${rideListResponse.e?.message}")
                    emit(Response.Failure(rideListResponse.e ?: Exception("Failed to get ride history")))
                }
                else -> {
                    println("Unexpected state in rideListResponse")
                    emit(Response.Failure(Exception("Unexpected state")))
                }
            }
        }
}