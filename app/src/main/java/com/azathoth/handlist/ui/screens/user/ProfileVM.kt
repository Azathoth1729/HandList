package com.azathoth.handlist.ui.screens.user

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.common.Resource
import com.azathoth.handlist.data.model.user.User
import com.azathoth.handlist.data.model.user.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserState(
    val isLoading: Boolean = false,
    val data: User? = null,
    val error: String = ""
)

@HiltViewModel
class ProfileVM @Inject constructor(
    private val userRepo: UserRepo,
    private val prefs: SharedPreferences
) : ViewModel() {
    var userState by mutableStateOf(UserState())
        private set

    init {
        viewModelScope.launch {
            val email = prefs.getString("userEmail", null)
            getAllUsersFlow(email).collect {
                userState = it
            }
        }
    }

    private fun getAllUsersFlow(email: String?) =
        findUserFlow(email).map {
            when (it) {
                is Resource.Success -> {
                    UserState(data = it.data)
                }

                is Resource.Error -> {
                    UserState(error = it.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    UserState(isLoading = true)
                }
            }
        }

    private fun findUserFlow(email: String?): Flow<Resource<User>> = flow {
        try {
            if (email == null) {
                emit(Resource.Error("Can't find login user"))
            } else {
                emit(Resource.Loading())
                val posts = userRepo.findUserByEmail(email)
                emit(Resource.Success(posts))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server.\nCheck your internet connection."))
        }
    }

}
