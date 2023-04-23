package com.azathoth.handlist.ui.screens.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.common.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import com.azathoth.handlist.data.usecase.task.GetTasksUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@HiltViewModel
class HomeVM @Inject constructor(private val usecase: GetTasksUseCase) : ViewModel() {
    private var _state by mutableStateOf(TaskListState())
    val state: TaskListState
        get() = _state

    init {
        getTasks()
    }

    private fun getTasks() {
        usecase().onEach {
            _state = when (it) {
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