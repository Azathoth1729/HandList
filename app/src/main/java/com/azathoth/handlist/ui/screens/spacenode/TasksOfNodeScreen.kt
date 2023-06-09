package com.azathoth.handlist.ui.screens.spacenode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.ui.share_comps.EditTopBar

import com.azathoth.handlist.ui.share_comps.TaskList

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
            onEdit = onMore,
        )
        TaskList(
            taskListState = viewModel.tasks,
            onTaskClick = navigateToTasksOfNode,
            modifier = modifier
        )
    }
}