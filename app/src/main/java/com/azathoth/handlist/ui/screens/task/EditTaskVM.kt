package com.azathoth.handlist.ui.screens.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.data.model.spacenode.SpaceNode
import com.azathoth.handlist.data.model.spacenode.SpaceNodeRepo
import com.azathoth.handlist.data.model.task.TaskRepo
import com.azathoth.handlist.data.model.task.TaskUiState
import com.azathoth.handlist.data.model.task.isValid
import com.azathoth.handlist.data.model.task.toTask
import com.azathoth.handlist.data.model.task.toUiState
import com.azathoth.handlist.data.model.user.User
import com.azathoth.handlist.data.model.user.UserRepo
import com.azathoth.handlist.ui.navigation.TaskEntryDest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditTaskVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: TaskRepo,
    private val nodeRepo: SpaceNodeRepo,
    private val userRepo: UserRepo
) : ViewModel() {
    private val taskId: Long = checkNotNull(savedStateHandle[TaskEntryDest.taskIdArg])

    var taskUiState by mutableStateOf(TaskUiState())
        private set

    var listNodes by mutableStateOf(listOf<SpaceNode>())

    var users by mutableStateOf(listOf<User>())

    init {
        viewModelScope.launch {
            taskUiState = repo.getTaskById(taskId)
                .toUiState()

            listNodes = nodeRepo.getAllListNodes()
            users = userRepo.getAllUsers()
        }
    }

    fun updateUiState(newTaskUiState: TaskUiState) {
        taskUiState = newTaskUiState
    }

    suspend fun updateTask() {
        if (taskUiState.isValid()) {
            val task = taskUiState.toTask()
            repo.updateTask(task.id, task)
        }
    }

    suspend fun deleteTask() {
        if (taskUiState.isValid()) {
            repo.deleteTask(taskUiState.toTask().id)
        }
    }

}