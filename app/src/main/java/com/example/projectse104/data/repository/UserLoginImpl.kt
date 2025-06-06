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

    override suspend fun verifyPassword(
        userId: String,
        currentPassword: String
    ): Response<Unit> = try {
        val user = usersRef.select {
            filter { User::id eq userId }
        }.decodeSingleOrNull<User>()

        if (user == null) {
            Response.Failure(Exception("Người dùng không tồn tại"))
        } else if (!BCrypt.checkpw(currentPassword, user.password)) {
            Response.Failure(Exception("Mật khẩu hiện tại không đúng"))
        } else {
            Response.Success(Unit)
        }
    } catch (e: Exception) {
        when {
            e.message?.contains("network") == true -> Response.Failure(Exception("Lỗi mạng"))
            else -> Response.Failure(Exception(e.message ?: "Lỗi không xác định"))
        }
    }

    override suspend fun updatePassword(
        userId: String,
        newPassword: String
    ): Response<Unit> = try {
        val hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt())
        usersRef.update({
            User::password setTo hashedNewPassword
        }) {
            filter { User::id eq userId }
        }
        Response.Success(Unit)
    } catch (e: Exception) {
        when {
            e.message?.contains("network") == true -> Response.Failure(Exception("Lỗi mạng"))
            else -> Response.Failure(Exception(e.message ?: "Lỗi không xác định"))
        }
    }
}