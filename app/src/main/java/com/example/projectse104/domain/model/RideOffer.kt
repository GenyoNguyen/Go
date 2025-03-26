package com.example.projectse104.domain.model

import com.example.projectse104.core.ACCEPTED_TIME_FIELD
import com.example.projectse104.core.COIN_COST_FIELD
import com.example.projectse104.core.END_LOCATION_FIELD
import com.example.projectse104.core.ESTIMATED_DEPART_TIME_FIELD
import com.example.projectse104.core.REQUEST_TIME_FIELD
import com.example.projectse104.core.START_LOCATION_FIELD
import com.example.projectse104.core.STATUS_FIELD
import com.example.projectse104.core.USER_ID_FIELD
import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date

data class RideOffer(
    val id: String,
    val userId: String? = null,
    val requestTime: Date? = null,
    val estimatedDepartTime: Date? = null,
    val startLocation: String? = null,
    val endLocation: String? = null,
    val coinCost: Int? = null,
    val status: String? = null,
    val acceptedTime: Date? = null
)

fun DocumentSnapshot.toRideOffer() = RideOffer(
    id = id,
    userId = getString(USER_ID_FIELD),
    requestTime = getDate(REQUEST_TIME_FIELD),
    estimatedDepartTime = getDate(ESTIMATED_DEPART_TIME_FIELD),
    startLocation = getString(START_LOCATION_FIELD),
    endLocation = getString(END_LOCATION_FIELD),
    coinCost = getLong(COIN_COST_FIELD)?.toInt(),
    status = getString(STATUS_FIELD),
    acceptedTime = getDate(ACCEPTED_TIME_FIELD)
)