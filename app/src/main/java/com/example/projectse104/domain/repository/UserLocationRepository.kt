package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.UserLocationWithLocation

typealias UserLocationWithLocationListResponse = Response<List<UserLocationWithLocation>>

interface UserLocationRepository {
    suspend fun getSavedLocationList(userId: String): UserLocationWithLocationListResponse
}