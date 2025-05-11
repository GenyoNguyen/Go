package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.repository.LoginResponse
import com.example.projectse104.domain.repository.UserLogin
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class UserLoginImpl @Inject constructor(
    private val usersRef: PostgrestQueryBuilder
) : UserLogin {

    override suspend fun login(email: String, password: String): LoginResponse = try {
        val user = usersRef.select {
            filter { User::email eq email }
        }.decodeSingleOrNull<User>()

        if (user == null) {
            Response.Failure(Exception("User with this email does not exist"))
        } else if (!BCrypt.checkpw(password, user.password)) {
            Response.Failure(Exception("Incorrect password"))
        } else {
            Response.Success(user)
        }
    } catch (e: Exception) {
        when {
            e.message?.contains("network") == true -> {
                Response.Failure(Exception("Network error"))
            }
            else -> Response.Failure(Exception(e.message ?: "Unknown error"))
        }
    }
}