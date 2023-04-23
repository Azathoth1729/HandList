package com.azathoth.handlist.ui.screens.home

import com.azathoth.handlist.data.model.task.TaskUiState

class TaskListState(
    val isLoading: Boolean = false,
    val tasks: List<TaskUiState> = emptyList(),
    val error: String = ""
)