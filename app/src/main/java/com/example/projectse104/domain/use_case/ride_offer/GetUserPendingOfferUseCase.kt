package com.example.projectse104.domain.use_case.ride_offer

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.model.RideOfferWithLocation
import com.example.projectse104.domain.repository.LocationRepository
import com.example.projectse104.domain.repository.RideOfferRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserPendingOfferUseCase @Inject constructor(
    private val rideOfferRepository: RideOfferRepository,
    private val locationRepository: LocationRepository
) {
    operator fun invoke(userId: String, page: Int, limit: Int): Flow<Response<List<RideOfferWithLocation>>> =
        flow {
            emit(Response.Loading)
            val from = (page * limit).toLong()
            val to = (from + limit + 1).toLong()
            when (val rideOfferListResponse = rideOfferRepository.getRideOfferListByUserIdPaginated(userId, "PENDING", from, to)) {
                is Response.Success<List<RideOffer>> -> {
                    println("Response is success, fetched ${rideOfferListResponse.data?.size} ride offers")
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
                                endLocation = endLocation
                            )
                        )
                    }
                    emit(Response.Success(rideOfferWithLocationList))
                }
                is Response.Failure -> {
                    println("Failed to get ride offers: ${rideOfferListResponse.e?.message}")
                    emit(Response.Failure(rideOfferListResponse.e ?: Exception("Failed to get ride history")))
                }
                else -> {
                    println("Unexpected state")
                    emit(Response.Failure(Exception("Unexpected state")))
                }
            }
        }
}