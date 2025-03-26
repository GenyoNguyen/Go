package com.example.projectse104.domain.model

data class Vehicle(
    val id: String,
    val driverId: Int? = null,
    val vehicleType: String? = null,
    val brand: String? = null,
    val model: String? = null,
    val seats: Int? = null,
    val plate: String? = null,
)
