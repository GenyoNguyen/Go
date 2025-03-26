package com.example.projectse104.core

// General id field
const val ID_FIELD = "id"

// User fields
const val FULL_NAME_FIELD = "fullName"
const val EMAIL_FIELD = "email"
const val PHONE_NUMBER_FIELD = "phoneNumber"
const val LOCATION_FIELD = "location"
const val PROFILE_PIC_FIELD = "profilePic"
const val OVERALL_RATING_FIELD = "overallRating"
const val COINS_FIELD = "coins"


// RideOffer fields
const val USER_ID_FIELD = "userId"
const val REQUEST_TIME_FIELD = "requestTime"
const val ESTIMATED_DEPART_TIME_FIELD = "estimatedDepartTime"
const val START_LOCATION_FIELD = "startLocation"
const val END_LOCATION_FIELD = "endLocation"
const val COIN_COST_FIELD = "coinCost"
const val STATUS_FIELD = "status"
const val ACCEPTED_TIME_FIELD = "acceptedTime"

// Ride fields
const val RIDE_OFFER_ID_FIELD = "rideOfferId"
const val PASSENGER_ID_FIELD = "passengerId"
const val DRIVER_ID_FIELD = "driverId"
const val DEPART_TIME_FIELD = "departTime"
const val ARRIVE_TIME_FIELD = "arriveTime"
const val RATING_FIELD = "rating"
const val COMMENT_FIELD = "comment"

// Conversation fields
const val RIDE_ID_FIELD = "rideId"

// Message fields
const val CONVERSATION_ID_FIELD = "conversationId"
const val SENDER_ID_FIELD = "senderId"
const val RECEIVER_ID_FIELD = "receiverId"
const val CONTENT_FIELD = "content"
const val TIME_SENT_FIELD = "timeSent"

// Vehicle fields
const val DRIVER_ID = "driverId"
const val VEHICLE_TYPE_FIELD = "vehicleType"
const val BRAND_FIELD = "brand"
const val MODEL_FIELD = "model"
const val SEATS_FIELD = "seats"
const val PLATE_NUMBER_FIELD = "plate"

enum class RideStatus(val value: String) {
    SUCCESS("success"),
    CANCELLED("cancelled"),
    PENDING("pending");
}