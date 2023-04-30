package com.azathoth.handlist.ui.screens.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

import com.azathoth.handlist.R
import com.azathoth.handlist.ui.components.MainTopBar
import com.azathoth.handlist.ui.components.TasksBody
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
        TasksBody(
            taskListState = viewModel.state,
            onTaskClick = navigateToEditTask,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenBodyPreview() {
    HandListTheme {
        TasksBody(
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
