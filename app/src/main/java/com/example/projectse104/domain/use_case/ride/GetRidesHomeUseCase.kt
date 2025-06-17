package com.example.projectse104.domain.use_case.ride

import com.example.projectse104.core.Response
import com.example.projectse104.core.RideStatus
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.model.RideWithRideOfferWithLocation
import com.example.projectse104.domain.repository.LocationRepository
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.RideRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetRidesHomeUseCase @Inject constructor(
    private val rideRepository: RideRepository,
    private val rideOfferRepository: RideOfferRepository,
    private val locationRepository: LocationRepository
) {
    operator fun invoke(userId: String, page: Int, limit: Int): Flow<Response<List<RideWithRideOfferWithLocation>>> =
        flow {
            emit(Response.Loading)
            val startTime = System.currentTimeMillis()
            val ridesWithLocations = try {
                coroutineScope {
                    val from = (page * limit).toLong()
                    val to = (from + limit - 1).toLong()

                    // Song song hóa truy vấn
                    val passengerRideDeferred = async {
                        rideRepository.getRideListGivenPassengerPaginated(userId, from, to)
                    }
                    val rideOfferDeferred = async {
                        rideOfferRepository.getRideOfferListByUserIdPaginated(userId, "ACCEPTED", from, to)
                    }

                    val passengerRideResponse = passengerRideDeferred.await()
                    val rideOfferList = rideOfferDeferred.await()
                    val rideOfferIds = when (rideOfferList) {
                        is Response.Success -> rideOfferList.data?.map { it.id } ?: emptyList()
                        else -> emptyList()
                    }
                    println("THIS IS THE RIDE IDS: $rideOfferIds")
                    val driverRideResponse = rideRepository.getRideListByRideOfferIds(rideOfferIds, from, to)

                    val combinedRideList = when {
                        passengerRideResponse is Response.Success && driverRideResponse is Response.Success -> {
                            val passengerRides = passengerRideResponse.data ?: emptyList()
                            val driverRides = driverRideResponse.data ?: emptyList()
                            (passengerRides + driverRides).distinctBy { it.id }
                        }
                        passengerRideResponse is Response.Success -> passengerRideResponse.data ?: emptyList()
                        driverRideResponse is Response.Success -> driverRideResponse.data ?: emptyList()
                        else -> {
                            return@coroutineScope null
                        }
                    }

                    val filteredRideList = combinedRideList.filter { it.status == RideStatus.PENDING }
                    val ridesWithLocations = mutableListOf<RideWithRideOfferWithLocation>()
                    val rideOfferCache = mutableMapOf<String, RideOffer>()
                    val locationIds = mutableSetOf<String>()

                    // Song song hóa tải rideOffer
                    val rideOfferJobs = filteredRideList.mapNotNull { ride ->
                        ride.rideOfferId?.let { rideOfferId ->
                            launch {
                                when (val rideOfferResponse = rideOfferRepository.getRideOffer(rideOfferId)) {
                                    is Response.Success -> {
                                        rideOfferResponse.data?.let { rideOffer ->
                                            synchronized(rideOfferCache) {
                                                rideOfferCache[rideOfferId] = rideOffer
                                            }
                                            synchronized(locationIds) {
                                                rideOffer.startLocationId?.let { locationIds.add(it) }
                                                rideOffer.endLocationId?.let { locationIds.add(it) }
                                            }
                                        }
                                    }
                                    is Response.Failure -> throw Exception("Cannot find RideOffer: ${rideOfferResponse.e?.message}")
                                    else -> throw Exception("Cannot find RideOffer")
                                }
                            }
                        }
                    }
                    rideOfferJobs.forEach { it.join() }

                    val locationResponse = locationRepository.getLocationListByIds(locationIds.toList())
                    val locations = when (locationResponse) {
                        is Response.Success -> locationResponse.data ?: emptyList()
                        else -> emptyList()
                    }
                    val locationMap = locations.associateBy { it.id }

                    filteredRideList.forEach { ride ->
                        val rideOffer = ride.rideOfferId?.let { id ->
                            rideOfferCache[id] ?: throw Exception("RideOffer not found in cache")
                        } ?: throw Exception("Không tìm thấy RideOffer")

                        val startLocation = rideOffer.startLocationId?.let { locationMap[it]?.name } ?: ""
                        val endLocation = rideOffer.endLocationId?.let { locationMap[it]?.name } ?: ""

                        ridesWithLocations.add(
                            RideWithRideOfferWithLocation(
                                ride = ride,
                                rideOffer = rideOffer,
                                startLocation = startLocation,
                                endLocation = endLocation
                            )
                        )
                    }

                    ridesWithLocations
                }
            } catch (e: Exception) {
                emit(Response.Failure(e))
                return@flow
            }

            if (ridesWithLocations == null) {
                emit(Response.Failure(Exception("Failed to get ride history from both passenger and driver")))
            } else {
                println("Time to fetch rides: ${System.currentTimeMillis() - startTime}ms")
                emit(Response.Success(ridesWithLocations))
            }
        }.flowOn(Dispatchers.IO)
}