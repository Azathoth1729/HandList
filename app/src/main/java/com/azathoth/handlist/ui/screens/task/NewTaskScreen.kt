package com.azathoth.handlist.ui.screens


import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

import com.azathoth.handlist.ui.components.TaskEntryBody
import com.azathoth.handlist.ui.screens.task.NewTaskVM


@Composable
fun NewTaskScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewTaskVM = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    TaskEntryBody(
        taskUiState = viewModel.taskUiState,
        listNodes = viewModel.listNodes,
        users = viewModel.users,
        onTaskValueChange = viewModel::updateUiState,
        onSaveClick = {
            coroutineScope.launch {
                viewModel.saveTask()
                navigateBack()
            }
        },
        isEdit = false,
        modifier = modifier
    )
}


