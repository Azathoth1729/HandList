package com.azathoth.handlist.ui.screens.task

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.data.model.spacenode.SpaceNode
import com.azathoth.handlist.data.model.spacenode.SpaceNodeRepo
import com.azathoth.handlist.data.model.task.TaskRepo
import com.azathoth.handlist.data.model.task.TaskUiState
import com.azathoth.handlist.data.model.task.isValid
import com.azathoth.handlist.data.model.task.toTask
import com.azathoth.handlist.data.model.user.User
import com.azathoth.handlist.data.model.user.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTaskVM @Inject constructor(
    private val taskRepo: TaskRepo,
    private val nodeRepo: SpaceNodeRepo,
    private val userRepo: UserRepo
) : ViewModel() {
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    var listNodes by mutableStateOf(listOf<SpaceNode>())

    var users by mutableStateOf(listOf<User>())

    init {
        viewModelScope.launch {
            listNodes = nodeRepo.getAllListNodes()
            users = userRepo.getAllUsers()
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
}

