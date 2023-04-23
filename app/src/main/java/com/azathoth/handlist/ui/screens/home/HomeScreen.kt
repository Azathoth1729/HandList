package com.azathoth.handlist.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.R

import com.azathoth.handlist.data.model.task.TaskUiState
import com.azathoth.handlist.ui.components.MainTopBar
import com.azathoth.handlist.ui.theme.HandListTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToEditTask: (Long) -> Unit = {},
    viewModel: HomeVM = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTopBar()
        HomeBody(
            taskListState = viewModel.state,
            onTaskClick = navigateToEditTask,
            modifier = modifier
        )
    }
}


@Composable
private fun HomeBody(
    taskListState: TaskListState,
    onTaskClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (taskListState.error.isNotBlank()) {
            Text(
                text = taskListState.error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (taskListState.isLoading) {
            Text(
                text = stringResource(R.string.loading_tasks),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (taskListState.tasks.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(taskListState.tasks) { task ->
                    TaskItem(task = task, onTaskClick = { onTaskClick(it.id) })
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskItem(
    task: TaskUiState,
    onTaskClick: (TaskUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.clickable { onTaskClick(task) }) {
        ListItem(
            headlineText = { Text(task.name) },
            supportingText = {
                Text("${task.startTime} to ${task.endTime}")
            },
            trailingContent = { task.status }
        )
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenBodyPreview() {
    HandListTheme {
        HomeBody(
            taskListState = TaskListState(
                tasks = listOf(),
            ),
            onTaskClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HandListTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = stringResource(R.string.loading_tasks),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}
