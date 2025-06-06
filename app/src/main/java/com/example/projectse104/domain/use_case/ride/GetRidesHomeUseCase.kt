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

class GetRidesHomeUseCase @Inject constructor(
    private val rideRepository: RideRepository,
    private val rideOfferRepository: RideOfferRepository,
    private val locationRepository: LocationRepository
) {
    operator fun invoke(userId: String, page: Int, limit: Int): Flow<Response<List<RideWithRideOfferWithLocation>>> =
        flow {
            emit(Response.Loading)

            // Tính toán phạm vi phân trang
            val from = (page * limit).toLong()
            val to = (from + limit + 1).toLong()

            // Lấy danh sách chuyến đi với hành khách có chung userId
            val passengerRideResponse = rideRepository.getRideListGivenPassengerPaginated(userId, from, to)
            // Lấy danh sách chuyến đi với tài xế có chung userId
            val rideOfferList = rideOfferRepository.getRideOfferListByUserIdPaginated(userId, "ACCEPTED", from, to)
            val rideIds = when (rideOfferList) {
                is Response.Success -> rideOfferList.data?.map { it.id } ?: emptyList()
                else -> emptyList()
            }
            println("THIS IS THE RIDE IDS: $rideIds")
            val driverRideResponse = rideRepository.getRideListByRideOfferIds(rideIds, from, to)

            // Kiểm tra và hợp nhất danh sách chuyến đi
            val combinedRideList = when {
                passengerRideResponse is Response.Success && driverRideResponse is Response.Success -> {
                    val passengerRides = passengerRideResponse.data ?: emptyList()
                    val driverRides = driverRideResponse.data ?: emptyList()
                    (passengerRides + driverRides).distinctBy { it.id }
                }
                passengerRideResponse is Response.Success -> {
                    passengerRideResponse.data ?: emptyList()
                }
                driverRideResponse is Response.Success -> {
                    driverRideResponse.data ?: emptyList()
                }
                else -> {
                    emit(Response.Failure(Exception("Failed to get ride history from both passenger and driver")))
                    return@flow
                }
            }

            // Lọc các chuyến đi có trạng thái PENDING
            val filteredRideList = combinedRideList.filter {
                it.status == RideStatus.PENDING
            }

            // Khởi tạo danh sách kết quả
            val ridesWithLocations = mutableListOf<RideWithRideOfferWithLocation>()

            // Xử lý từng chuyến đi
            filteredRideList.forEach { ride ->
                val rideOffer = ride.rideOfferId?.let { id ->
                    when (val rideOfferResponse = rideOfferRepository.getRideOffer(id)) {
                        is Response.Success -> rideOfferResponse.data
                            ?: throw Exception("RideOffer data is null")
                        is Response.Failure -> throw Exception("Cannot find RideOffer for this ride: ${rideOfferResponse.e?.message}")
                        else -> throw Exception("Cannot find RideOffer for this ride")
                    }
                } ?: throw Exception("Không tìm thấy RideOffer cho chuyến đi này")

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
            }

            emit(Response.Success(ridesWithLocations))
        }
}