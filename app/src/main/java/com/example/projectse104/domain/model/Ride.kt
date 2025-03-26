package com.example.projectse104.domain.model

import com.example.projectse104.core.RideStatus
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import java.util.Date

data class Ride(
    @Exclude
    val id: String,
    val rideOfferId: String? = null,
    val passengerId: String? = null,
    val driverId: String? = null,
    val departTime: Date? = null,
    val arriveTime: Date? = null,
    val status: RideStatus = RideStatus.PENDING,
    val rating: Float? = null,
    val comment: String? = null
)

fun DocumentSnapshot.toRide() = Ride(
    id = id,
    rideOfferId = getString("rideOfferId"),
    passengerId = getString("passengerId"),
    driverId = getString("driverId"),
    departTime = getDate("departTime"),
    arriveTime = getDate("arriveTime"),
    status = RideStatus.valueOf(getString("status") ?: "PENDING"),
    rating = getDouble("rating")?.toFloat(),
    comment = getString("comment")
)