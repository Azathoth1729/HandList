package com.azathoth.handlist.ui.screens.home

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.common.Resource
import com.azathoth.handlist.data.model.task.TaskUiState
import com.azathoth.handlist.data.usecase.task.GetTasksByUserEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TaskListState(
    val isLoading: Boolean = false,
    val tasks: List<TaskUiState> = emptyList(),
    val error: String = ""
)

@HiltViewModel
class HomeVM @Inject constructor(
    private val usecase: GetTasksByUserEmailUseCase,
    prefs: SharedPreferences
) : ViewModel() {
    var tasks by mutableStateOf(TaskListState())
        private set

    init {
        getTasks(prefs.getString("userEmail", null))
    }

    private fun getTasks(email: String?) {
        usecase(email).onEach {
            tasks = when (it) {
                is Resource.Success -> {
                    TaskListState(tasks = it.data ?: emptyList())
                }

                is Resource.Error -> {
                    TaskListState(error = it.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    TaskListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

