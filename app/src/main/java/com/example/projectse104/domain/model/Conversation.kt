package com.example.projectse104.domain.model

import com.google.firebase.firestore.Exclude

data class Conversation(
    @Exclude
    val id: String,
    val rideId: String? = null
)
