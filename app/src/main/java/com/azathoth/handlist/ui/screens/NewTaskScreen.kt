package com.azathoth.handlist.ui.screens


import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

import com.azathoth.handlist.ui.AppViewModelProvider
import com.azathoth.handlist.ui.components.TaskEntryBody
import com.azathoth.handlist.ui.viewmodels.NewTaskVM


@Composable
fun NewTaskScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewTaskVM = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    TaskEntryBody(
        taskUiState = viewModel.taskUiState,
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


