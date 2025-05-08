package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.UserLocationWithLocation
import com.example.projectse104.domain.repository.UserLocationRepository
import com.example.projectse104.domain.repository.UserLocationWithLocationListResponse
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder

class UserLocationRepositoryImpl(
    private val userLocationRef: PostgrestQueryBuilder
) : UserLocationRepository {
    override suspend fun getSavedLocationList(userId: String): UserLocationWithLocationListResponse =
        try {
            val columns = Columns.list("userId", "locationId", "type", "location:Location(*)")
            val userLocationWithLocationList = userLocationRef.select(columns = columns) {
                filter {
                    eq("userId", userId)
                }
            }.decodeList<UserLocationWithLocation>()
            Response.Success(userLocationWithLocationList)
        } catch (e: Exception) {
            Response.Failure(e)
        }
}