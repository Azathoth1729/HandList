package com.azathoth.handlist.ui.screens.spacenode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.common.Resource
import com.azathoth.handlist.data.usecase.task.GetTasksByNodeUseCase
import com.azathoth.handlist.ui.navigation.TasksOfNodeDest
import com.azathoth.handlist.ui.screens.home.TaskListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TasksOfNodeVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val usecase: GetTasksByNodeUseCase
) : ViewModel() {
    var state by mutableStateOf(TaskListState())
        private set

    private val nodeId: Long = checkNotNull(savedStateHandle[TasksOfNodeDest.nodeIdArg])

    init {
        getTasks(nodeId)
    }

    private fun getTasks(nodeId: Long) {
        usecase(nodeId).onEach {
            state = when (it) {
                is Resource.Success -> {
                    TaskListState(tasks = it.data ?: emptyList())
                }
                is Resource.Error -> {
                    TaskListState(
                        error = it.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    TaskListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}