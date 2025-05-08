package com.example.projectse104.domain.use_case.favourite_rider

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.UserFavouriteRiderRepository
import com.example.projectse104.domain.repository.UserListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRiderListFromUserFavouriteRiderUseCase @Inject constructor(
    private val userFavouriteRiderRepository: UserFavouriteRiderRepository
) {
    operator fun invoke(riderId: String): Flow<UserListResponse> = flow {
        emit(Response.Loading)
        emit(userFavouriteRiderRepository.getRiderListFromUserFavouriteRider(riderId))
    }
}