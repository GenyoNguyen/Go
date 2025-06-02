package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Location
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.repository.LocationListResponse
import com.example.projectse104.domain.repository.LocationRepository
import com.example.projectse104.domain.repository.LocationResponse
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder

class LocationRepositoryImpl(
    private val locationRef: PostgrestQueryBuilder
) : LocationRepository {
    override suspend fun getLocation(locationId: String): LocationResponse = try {
        val location = locationRef.select {
            filter {
                eq("id", locationId)
            }
        }.decodeSingle<Location>()
        Response.Success(location)
    } catch (e: Exception) {
        Response.Failure(e)
    }
    override suspend fun getLocationList(): LocationListResponse =
        try {
            val rideOffers = locationRef.select().decodeList<Location>()
            Response.Success(rideOffers)
        } catch (e: Exception) {
            Response.Failure(e)
        }
} 