package com.azathoth.handlist.ui.screens.spacenode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.ui.components.EditTopBar

import com.azathoth.handlist.ui.components.TasksBody

@Composable
fun TasksOfNodeScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = { },
    onMore: () -> Unit = { },
    navigateToTasksOfNode: (Long) -> Unit = {},
    viewModel: TasksOfNodeVM = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditTopBar(
            navigateBack = navigateBack,
            onMore = onMore,
        )
        TasksBody(
            taskListState = viewModel.state,
            onTaskClick = navigateToTasksOfNode,
            modifier = modifier
        )
    }
}