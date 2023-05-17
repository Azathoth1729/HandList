package com.azathoth.handlist.ui.screens.task


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.ui.share_comps.MainTopBar
import com.azathoth.handlist.ui.share_comps.TaskEditBody
import kotlinx.coroutines.launch


@Composable
fun NewTaskScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = { },
    navigateToProfile: () -> Unit = { },
    viewModel: NewTaskVM = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTopBar(navigateToProfile = navigateToProfile)
        TaskEditBody(
            taskUiState = viewModel.taskUiState,
            listNodes = viewModel.listNodes.data,
            users = viewModel.users.data,
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

}


