

package com.example.projectse104.domain.use_case.user
import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.UserLogin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val userLogin: UserLogin
) {
    operator fun invoke(
        userId: String,
        newPassword: String
    ): Flow<Response<Unit>> = flow {
        emit(Response.Loading)
        try {
            val result = userLogin.updatePassword(userId, newPassword)
            when (result) {
                is Response.Success -> emit(Response.Success(Unit))
                is Response.Failure -> emit(Response.Failure(result.e))
                else -> emit(Response.Failure(Exception("Lỗi không xác định khi đổi mật khẩu")))
            }
        } catch (e: Exception) {
            emit(Response.Failure(Exception(e.message ?: "Lỗi không xác định")))
        }
    }
}
