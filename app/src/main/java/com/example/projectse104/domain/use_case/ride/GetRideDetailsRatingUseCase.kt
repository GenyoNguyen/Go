package com.example.projectse104.domain.use_case.ride

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.RideWithPassengerAndRider
import com.example.projectse104.domain.repository.ConversationRepository
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRideDetailsRatingUseCase @Inject constructor(
    private val rideRepository: RideRepository,
    private val rideOfferRepository: RideOfferRepository,
    private val userRepository: UserRepository,
    private val conversationRepository: ConversationRepository
) {
    operator fun invoke(rideId: String): Flow<Response<RideWithPassengerAndRider>> =
        flow {
            emit(Response.Loading)
            when (val rideListResponse = rideRepository.getRide(rideId)) {
                is Response.Success<Ride> -> {
                    val ride = rideListResponse.data!!

                    val rideOffer = ride.rideOfferId?.let { rideOfferId ->
                        when (val rideOfferResponse =
                            rideOfferRepository.getRideOffer(rideOfferId)) {
                            is Response.Success -> rideOfferResponse.data
                                ?: throw Exception("Ride offer data is null")

                            else -> throw Exception("Cannot find Ride Offer for this ride.")
                        }
                    } ?: throw Exception("Cannot find Ride Offer for this ride")

                    val rider = rideOffer.userId?.let { userId ->
                        when (val userResponse = userRepository.getUser(userId)) {
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

                    when (val conversationResponse =
                        conversationRepository.getOrCreateConversation(
                            rideOffer.userId,
                            ride.passengerId
                        )) {
                        is Response.Success -> {
                            val conversationId = conversationResponse.data?.id
                                ?: throw Exception("Conversation ID is null")
                            emit(
                                Response.Success(
                                    RideWithPassengerAndRider(
                                        ride = ride,
                                        rideOffer = rideOffer,
                                        rider = rider,
                                        passenger = passenger,
                                        conversationId = conversationId
                                    )
                                )
                            )
                        }

                        else -> throw Exception("Failed to get or create conversation")
                    }
                }

                else -> emit(Response.Failure(Exception("Failed to get ride history details")))
            }
        }
}
