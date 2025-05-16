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
    operator fun invoke(userId: String): Flow<Response<List<RideWithRideOfferWithLocation>>> =
        flow {
            emit(Response.Loading)

            // Lấy danh sách chuyến đi với hành khách có chung userId
            val passengerRideResponse = rideRepository.getRideListGivenPassenger(userId)
            // Lấy danh sách chuyến đi với tài xế có chung userId
            val rideOfferList=rideOfferRepository.getRideOfferListByUserId(userId,"ACCEPTED")
            val rideIds = when (rideOfferList) {
                is Response.Success -> rideOfferList.data?.map { it.id }?: emptyList()
                else -> emptyList()
            }
            print("THIS IS THE RIDE IDS: $rideIds")
            val driverRideResponse = rideRepository.getRideListByRideOfferIds(rideIds)
            // Kiểm tra và hợp nhất danh sách chuyến đi
            val combinedRideList = when {
                passengerRideResponse is Response.Success && driverRideResponse is Response.Success -> {
                    // Hợp nhất hai danh sách và loại bỏ trùng lặp dựa trên ride.id
                    val passengerRides = passengerRideResponse.data ?: emptyList()
                    val driverRides = driverRideResponse.data ?: emptyList()
                    (passengerRides + driverRides).distinctBy { it.id }
                }
                passengerRideResponse is Response.Success -> {
                    // Chỉ dùng danh sách hành khách nếu tài xế thất bại
                    passengerRideResponse.data ?: emptyList()
                }
                driverRideResponse is Response.Success -> {
                    // Chỉ dùng danh sách tài xế nếu hành khách thất bại
                    driverRideResponse.data ?: emptyList()
                }
                else -> {
                    // Cả hai đều thất bại
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
                    when (val rideOfferResponse = rideOfferRepository.getRideOffer(rideOfferId = id)) {
                        is Response.Success -> rideOfferResponse.data
                            ?: throw Exception("RideOffer data is null")
                        else -> throw Exception("Cannot find RideOffer for this ride.")
                    }
                } ?: throw Exception("Cannot find RideOffer for this ride")

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
                        endLocation = endLocation,
                    )
                )
            }

            emit(Response.Success(ridesWithLocations))
        }
}