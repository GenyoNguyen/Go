package com.example.projectse104.domain.model

import com.example.projectse104.core.COINS_FIELD
import com.example.projectse104.core.EMAIL_FIELD
import com.example.projectse104.core.FULL_NAME_FIELD
import com.example.projectse104.core.LOCATION_FIELD
import com.example.projectse104.core.OVERALL_RATING_FIELD
import com.example.projectse104.core.PHONE_NUMBER_FIELD
import com.example.projectse104.core.PROFILE_PIC_FIELD
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

data class User(
    @Exclude
    val id: String,
    val fullName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phoneNumber: String? = null,
    val location: String? = null,
    val profilePic: String? = null,
    val overallRating: Float? = null,
    val coins: Int? = null
)

fun DocumentSnapshot.toUser() = User(
    id = id,
    fullName = getString(FULL_NAME_FIELD),
    email = getString(EMAIL_FIELD),
    phoneNumber = getString(PHONE_NUMBER_FIELD),
    location = getString(LOCATION_FIELD),
    profilePic = getString(PROFILE_PIC_FIELD),
    overallRating = getDouble(OVERALL_RATING_FIELD)?.toFloat(),
    coins = getLong(COINS_FIELD)?.toInt()
)