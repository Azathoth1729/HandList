package com.azathoth.handlist.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.azathoth.handlist.data.task.TaskUiState
import com.azathoth.handlist.data.task.TasksRepository
import com.azathoth.handlist.data.task.isValid
import com.azathoth.handlist.data.task.toTask

class NewTaskVM(private val tasksRepository: TasksRepository) : ViewModel() {
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    fun updateUiState(newTaskUiState: TaskUiState) {
        taskUiState = newTaskUiState
    }

    suspend fun saveTask() {
        if (taskUiState.isValid()) {
            tasksRepository.insertTask(taskUiState.toTask())
        }
    }
}