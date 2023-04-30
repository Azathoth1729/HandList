package com.azathoth.handlist.data.usecase.user

import com.azathoth.handlist.common.Resource
import com.azathoth.handlist.data.model.user.User
import com.azathoth.handlist.data.model.user.UserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repo: UserRepo
) {
    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading())
            val users = repo.getAllUsers()
            emit(Resource.Success(users))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}