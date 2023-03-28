package com.azathoth.handlist.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.data.task.*
import com.azathoth.handlist.ui.navigation.TaskEntryDest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditTaskVM(
    savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository
) : ViewModel() {
    private val taskId: Int = checkNotNull(savedStateHandle[TaskEntryDest.taskIdArg])

    var taskUiState by mutableStateOf(TaskUiState())
        private set

    init {
        viewModelScope.launch {
            taskUiState = tasksRepository.getTaskStream(taskId)
                .filterNotNull()
                .first()
                .toUiState()
        }
    }

    fun updateUiState(newTaskUiState: TaskUiState) {
        taskUiState = newTaskUiState
    }

    suspend fun updateTask() {
        if (taskUiState.isValid()) {
            tasksRepository.updateTask(taskUiState.toTask())
        }
    }

    suspend fun deleteTask() {
        if (taskUiState.isValid()) {
            tasksRepository.deleteTask(taskUiState.toTask())
        }
    }

}