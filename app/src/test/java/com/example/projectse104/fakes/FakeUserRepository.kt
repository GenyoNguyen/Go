package com.example.projectse104.fakes

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.repository.AddUserResponse
import com.example.projectse104.domain.repository.DeleteUserResponse
import com.example.projectse104.domain.repository.UpdateUserResponse
import com.example.projectse104.domain.repository.UploadProfilePicResponse
import com.example.projectse104.domain.repository.UserRepository
import com.example.projectse104.domain.repository.UserResponse

class FakeUserRepository : UserRepository {

    private val user = User(
        id = "1",
        fullName = "Alice in Wonderland",
        email = "alice@gmail.com",
        password = "password",
        phoneNumber = "1234567890",
        location = "Wonderland",
        overallRating = 4.5f,
        coins = 100,
        userCode = "ALICE"
    )

    private var shouldReturnNetworkError = false


    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getUser(userId: String): UserResponse =
        if (shouldReturnNetworkError) {
            Response.Failure(Exception("Network error"))
        } else {
            Response.Success(user)
        }

    override suspend fun insertUser(user: User): AddUserResponse =
        if (shouldReturnNetworkError) {
            Response.Failure(Exception("Network error"))
        } else {
            Response.Success(Unit)
        }

    override suspend fun updateUser(userUpdate: Map<String, String>): UpdateUserResponse =
        if (shouldReturnNetworkError) {
            Response.Failure(Exception("Network error"))
        } else {
            Response.Success(Unit)
        }

    override suspend fun deleteUser(userId: String): DeleteUserResponse =
        if (shouldReturnNetworkError) {
            Response.Failure(Exception("Network error"))
        } else {
            Response.Success(Unit)
        }

    override suspend fun uploadProfilePic(
        userId: String,
        imageUri: String
    ): UploadProfilePicResponse {
        TODO("Not yet implemented")
    }

    override suspend fun updateEmailVerificationStatus(
        firebaseUid: String,
        isVerified: Boolean
    ): Response<Unit> {
        TODO("Not yet implemented")
    }
}