package com.azathoth.handlist.ui.share_comps

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.azathoth.handlist.R
import com.azathoth.handlist.data.Status
import com.azathoth.handlist.data.model.task.TaskUiState
import com.azathoth.handlist.ui.screens.home.TaskListState

@Composable
fun TaskList(
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
            // classify tasks by their status
            val tasksByStatus = taskListState.tasks.groupBy { Status.valueOf(it.status) }
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(Status.values()) { status ->
                    Text(
                        text = status.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
//                        colors = ,
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        tasksByStatus[status]?.forEach { task ->
                            TaskItem(
                                task = task,
                                onTaskClick = { onTaskClick(it.id) },
                                modifier = Modifier.padding(4.dp).padding(horizontal = 8.dp)
                            )
                            Divider()

                        }
                    }


                }

            }
        }
    }
}

@Composable
fun TaskItem(
    task: TaskUiState,
    onTaskClick: (TaskUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .clickable { onTaskClick(task) }) {


        Text(
            text = task.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "${task.startTime} to ${task.endTime}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline
        )
        task.spaceNode?.let {
            Text(
                text = it.path,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline,

                )
        }

        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis
        )


    }
}

