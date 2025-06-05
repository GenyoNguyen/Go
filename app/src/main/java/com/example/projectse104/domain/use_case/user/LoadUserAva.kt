package com.example.projectse104.domain.use_case.user

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.UserRepository
import javax.inject.Inject

class LoadUserAva @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Response<String> = try {
        when (val userResponse = userRepository.getUser(userId)) {
            is Response.Success -> {
                val profilePic = userResponse.data?.profilePic
                if (profilePic.isNullOrEmpty()) {
                    Response.Failure(Exception("User has no profile picture"))
                } else {
                    Response.Success(profilePic)
                }
            }
            is Response.Failure -> {
                Response.Failure(userResponse.e) // Use 'e' to match Response.Failure
            }
            else -> {
                Response.Failure(Exception("Unexpected response state"))
            }
        }
    } catch (e: Exception) {
        Response.Failure(Exception("Failed to load user avatar: ${e.message}"))
    }
}