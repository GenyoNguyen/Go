package com.example.projectse104.data.repository

import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.repository.AcceptRideOfferResponse
import com.example.projectse104.domain.repository.AcceptedRideOfferListResponse
import com.example.projectse104.domain.repository.AddRideOfferResponse
import com.example.projectse104.domain.repository.RideOfferListResponse
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.RideOfferResponse
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.flow.Flow

class RideOfferRepositoryImpl(
    private val rideOffersRef: PostgrestQueryBuilder
) : RideOfferRepository {
    //    override fun getRideOfferList(): Flow<RideOfferListResponse> = callbackFlow {
//        val listener = rideOffersRef.orderBy(ESTIMATED_DEPART_TIME_FIELD)
//            .addSnapshotListener { rideOfferListSnapshot, e ->
//                val rideOfferListResponse = if (rideOfferListSnapshot != null) {
//                    val rideOfferList = rideOfferListSnapshot.map { rideOfferSnapshot ->
//                        rideOfferSnapshot.toRideOffer()
//                    }
//                    Response.Success(rideOfferList)
//                } else {
//                    Response.Failure(e)
//                }
//                trySend(rideOfferListResponse)
//            }
//        awaitClose {
//            listener.remove()
//        }
//    }
//
//    override suspend fun getRideOffer(rideOfferId: String): RideOfferResponse = try {
//        val rideOfferSnapshot = rideOffersRef.document(rideOfferId).get().await()
//        Response.Success(rideOfferSnapshot.toRideOffer())
//    } catch (e: Exception) {
//        Response.Failure(e)
//    }
//
//    override fun getAcceptedRideOfferList(userId: String): Flow<AcceptedRideOfferListResponse> =
//        callbackFlow {
//            val listener = rideOffersRef.whereNotEqualTo(ACCEPTED_TIME_FIELD, null)
//                .orderBy(ESTIMATED_DEPART_TIME_FIELD)
//                .addSnapshotListener { rideOfferListSnapshot, e ->
//                    val rideOfferListResponse = if (rideOfferListSnapshot != null) {
//                        val rideOfferList = rideOfferListSnapshot.map { rideOfferSnapshot ->
//                            rideOfferSnapshot.toRideOffer()
//                        }
//                        Response.Success(rideOfferList)
//                    } else {
//                        Response.Failure(e)
//                    }
//                    trySend(rideOfferListResponse)
//                }
//            awaitClose {
//                listener.remove()
//            }
//        }
//
//    override suspend fun acceptRideOffer(rideOfferId: String): AcceptRideOfferResponse = try {
//        val currentDateTime = Date()
//        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        val formattedDateTime = formatter.format(currentDateTime)
//
//        rideOffersRef.document(rideOfferId).update(ACCEPTED_TIME_FIELD, formattedDateTime).await()
//        Response.Success(Unit)
//    } catch (e: Exception) {
//        Response.Failure(e)
//    }
//
//    override suspend fun addRideOffer(rideOffer: RideOffer): AddRideOfferResponse = try {
//        rideOffersRef.add(rideOffer)
//        Response.Success(Unit)
//    } catch (e: Exception) {
//        Response.Failure(e)
//    }
    override suspend fun getRideOffer(rideOfferId: String): RideOfferResponse {
        TODO("Not yet implemented")
    }

    override fun getRideOfferList(): Flow<RideOfferListResponse> {
        TODO("Not yet implemented")
    }

    override fun getAcceptedRideOfferList(userId: String): Flow<AcceptedRideOfferListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun acceptRideOffer(rideOfferId: String): AcceptRideOfferResponse {
        TODO("Not yet implemented")
    }

    override suspend fun addRideOffer(rideOffer: RideOffer): AddRideOfferResponse {
        TODO("Not yet implemented")
    }

}

