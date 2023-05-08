package com.azathoth.handlist.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.common.Resource
import com.azathoth.handlist.data.usecase.task.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class HomeVM @Inject constructor(private val usecase: GetTasksUseCase) : ViewModel() {
    var tasks by mutableStateOf(TaskListState())
        private set

    init {
        getTasks()
    }

    private fun getTasks() {
        usecase().onEach {
            tasks = when (it) {
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