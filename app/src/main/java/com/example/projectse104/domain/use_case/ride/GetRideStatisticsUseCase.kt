package com.example.projectse104.domain.use_case.ride

import com.example.projectse104.core.Response
import com.example.projectse104.core.RideStatus
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.UserRepository
import javax.inject.Inject

// Data class để lưu cặp Ride và RideOffer
data class RideRideOffer(
    val ride: Ride,
    val rideOffer: RideOffer,
)

// Data class để lưu kết quả thống kê
data class RideStatistics(
    val rideGiven: Int,
    val rideTaken: Int,
    val trustScore: Int,
    val last2ridesUserId: List<User?>
)

class GetRideStatisticsUseCase @Inject constructor(
    private val rideRepository: RideRepository,
    private val rideOfferRepository: RideOfferRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Response<RideStatistics> {
        try {
            // Lấy danh sách tất cả Ride
            val rideListResponse = rideRepository.getSuccessRideList()
            if (rideListResponse !is Response.Success || rideListResponse.data == null) {
                println("Failed to fetch rides: $rideListResponse")
                return Response.Failure(Exception("Failed to fetch rides or rides are null"))
            }
            val rides = rideListResponse.data

            // Lấy danh sách tất cả RideOffer
            val rideOfferListResponse = rideOfferRepository.getSuccessRideOfferList()
            if (rideOfferListResponse !is Response.Success || rideOfferListResponse.data == null) {
                println("Failed to fetch ride offers: $rideOfferListResponse")
                return Response.Failure(Exception("Failed to fetch ride offers or ride offers are null"))
            }
            val rideOffers = rideOfferListResponse.data

            // Gộp Ride và RideOffer dựa trên rideOfferId
            val rideRideOffers = rides.mapNotNull { ride ->
                val rideOffer = rideOffers.find { it.id == ride.rideOfferId }
                rideOffer?.let { RideRideOffer(ride, it) }
            }
            val rideTaken = rideRideOffers.count { it.ride.passengerId == userId }
            val rideGivenList = rideRideOffers.filter { it.rideOffer.userId == userId }
            val rideGiven = rideGivenList.size

            // Tính độ tin tưởng
            val passengerIdCount = mutableMapOf<String, Int>()
            for (r in rideGivenList) {
                val pid = r.ride.passengerId
                if (pid != null) {
                    passengerIdCount[pid] = passengerIdCount.getOrDefault(pid, 0) + 1
                }
            }
            fun trustFunc(n: Int): Double {
                return 1 + 5.5 * (n - 1)
            }
            val trustScore = passengerIdCount.values.sumOf { trustFunc(it) }
            val last2rides = rideRideOffers.take(2)
            val last2ridesUserId = last2rides.mapNotNull { rideRideOffer ->
                if (rideRideOffer.ride.passengerId == userId) {
                    rideRideOffer.rideOffer.userId
                } else {
                    rideRideOffer.ride.passengerId
                }
            }
            // Initialize last2ridesUser as a MutableList
            val last2ridesUser: MutableList<User?> = mutableListOf()
            // Lấy danh sách user từ last2ridesUserId
            for (id in last2ridesUserId) {
                val userResponse = userRepository.getUser(id)
                if (userResponse !is Response.Success || userResponse.data == null) {
                    println("Failed to fetch user: $userResponse")
                    return Response.Failure(Exception("Failed to fetch user or user is null"))
                }
                last2ridesUser.add(userResponse.data)
            }
            return Response.Success(
                RideStatistics(
                    rideGiven = rideGiven,
                    rideTaken = rideTaken,
                    trustScore = trustScore.toInt(),
                    last2ridesUserId = last2ridesUser
                )
            )
        } catch (e: Exception) {
            println("Error in GetRideStatisticsUseCase: $e")
            return Response.Failure(e)
        }
    }
}