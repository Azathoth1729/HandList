package com.azathoth.handlist.ui.screens.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.common.Resource
import com.azathoth.handlist.data.model.spacenode.SpaceNode
import com.azathoth.handlist.data.model.spacenode.SpaceNodeRepo
import com.azathoth.handlist.data.model.task.TaskRepo
import com.azathoth.handlist.data.model.task.TaskUiState
import com.azathoth.handlist.data.model.task.isValid
import com.azathoth.handlist.data.model.task.toTask
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

class NodeState(
    val isLoading: Boolean = false,
    val data: List<SpaceNode> = emptyList(),
    val error: String = ""
)

class UserState(
    val isLoading: Boolean = false,
    val data: List<User> = emptyList(),
    val error: String = ""
)


@HiltViewModel
class NewTaskVM @Inject constructor(
    private val taskRepo: TaskRepo,
    private val nodeRepo: SpaceNodeRepo,
    private val userRepo: UserRepo
) : ViewModel() {
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    var listNodes by mutableStateOf(NodeState())

    var users by mutableStateOf(UserState())

    init {
        viewModelScope.launch {
            getAllNodesFlow().collect {
                listNodes = it
            }
            getAllUsersFlow().collect {
                users = it
            }
        }
    }

    fun updateUiState(newTaskUiState: TaskUiState) {
        taskUiState = newTaskUiState
    }


    suspend fun saveTask() {
        if (taskUiState.isValid()) {
            taskUiState.spaceNode?.let {
                taskRepo.insertTask(it.id, taskUiState.toTask())
            }
        }
    }

    private fun getAllNodesFlow() =
        allNodesFlow().map {
            when (it) {
                is Resource.Success -> {
                    NodeState(data = it.data ?: emptyList())
                }

                is Resource.Error -> {
                    NodeState(error = it.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    NodeState(isLoading = true)
                }
            }
        }

    private fun getAllUsersFlow() =
        allUsersFlow().map {
            when (it) {
                is Resource.Success -> {
                    UserState(data = it.data ?: emptyList())
                }

                is Resource.Error -> {
                    UserState(error = it.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    UserState(isLoading = true)
                }
            }
        }

    private fun allNodesFlow(): Flow<Resource<List<SpaceNode>>> = flow {
        try {
            emit(Resource.Loading())
            val posts = nodeRepo.getAllListNodes()
            emit(Resource.Success(posts))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server.\nCheck your internet connection."))
        }
    }

    private fun allUsersFlow(): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading())
            val posts = userRepo.getAllUsers()
            emit(Resource.Success(posts))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server.\nCheck your internet connection."))
        }
    }
}

