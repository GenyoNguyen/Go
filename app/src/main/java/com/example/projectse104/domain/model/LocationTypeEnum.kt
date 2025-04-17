package com.example.projectse104.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class LocationTypeEnum(val nameType: String) {
    HOME("Home"),
    WORK("Work"),
    OTHER("Other");
}