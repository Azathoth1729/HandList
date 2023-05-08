package com.azathoth.handlist.ui.screens.task


import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.ui.share_comps.TaskEditBody
import kotlinx.coroutines.launch


@Composable
fun NewTaskScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewTaskVM = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    TaskEditBody(
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


