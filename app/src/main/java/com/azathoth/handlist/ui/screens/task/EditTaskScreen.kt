package com.azathoth.handlist.ui.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.ui.share_comps.EditTopBar
import com.azathoth.handlist.ui.share_comps.TaskEditBody
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = { },
    onMore: () -> Unit = { },
    viewModel: EditTaskVM = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            EditTopBar(
                navigateBack = navigateBack,
                onEdit = onMore,
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        TaskEditBody(
            taskUiState = viewModel.taskUiState,
            listNodes = viewModel.listNodes,
            users = viewModel.users,
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