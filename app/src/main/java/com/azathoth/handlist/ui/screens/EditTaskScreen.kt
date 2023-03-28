package com.azathoth.handlist.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azathoth.handlist.ui.AppViewModelProvider
import com.azathoth.handlist.ui.components.EditTopBar
import com.azathoth.handlist.ui.components.TaskEntryBody
import com.azathoth.handlist.ui.viewmodels.EditTaskVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = { },
    onMore: () -> Unit = { },
    viewModel: EditTaskVM = viewModel(factory = AppViewModelProvider.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            EditTopBar(
                navigateBack = navigateBack,
                onMore = onMore,
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        TaskEntryBody(
            taskUiState = viewModel.taskUiState,
            onTaskValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTask()
                    snackbarHostState.showSnackbar(
                        "Task have saved"
                    )
                }
            },
            onDeleteClick = {
                coroutineScope.launch {
                    viewModel.deleteTask()
                    navigateBack()
                }
            },
            isEdit = true,
            modifier = modifier.padding(innerPadding)
        )
    }

}