package com.azathoth.handlist.ui.screens

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
import androidx.lifecycle.viewmodel.compose.viewModel

import com.azathoth.handlist.ui.viewmodels.HomeVM
import com.azathoth.handlist.R
import com.azathoth.handlist.data.task.Status
import com.azathoth.handlist.data.task.Task
import com.azathoth.handlist.ui.AppViewModelProvider
import com.azathoth.handlist.ui.components.MainTopBar
import com.azathoth.handlist.ui.theme.HandListTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToEditTask: (Int) -> Unit = {},
    viewModel: HomeVM = viewModel(factory = AppViewModelProvider.Factory),
) {
    val homeUiState by viewModel.homeUiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTopBar()
        HomeBody(
            taskList = homeUiState.taskList,
            onTaskClick = navigateToEditTask,
            modifier = modifier
        )
    }
}


@Composable
private fun HomeBody(
    taskList: List<Task>,
    onTaskClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (taskList.isEmpty()) {
        Text(
            text = stringResource(R.string.no_item_description),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier
//                .offset(y = 100.dp)
                .padding(40.dp)
        )
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(taskList) { task ->
                TaskItem(task = task, onTaskClick = { onTaskClick(it.id) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskItem(
    task: Task,
    onTaskClick: (Task) -> Unit,
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
            listOf(
                Task(1, "Game", Status.InProgress.toString(), "hello"),
                Task(2, "Pen", Status.Done.toString(), "world"),
                Task(3, "TV", Status.Closed.toString(), "kotlin")
            ),
            onTaskClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HandListTheme {
        HomeScreen()
    }
}
