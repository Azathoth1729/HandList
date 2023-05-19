package com.azathoth.handlist.ui.share_comps

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azathoth.handlist.R
import com.azathoth.handlist.common.util.toDateStatus
import com.azathoth.handlist.common.util.toLocalDate
import com.azathoth.handlist.data.Status
import com.azathoth.handlist.data.model.task.TaskUiState
import com.azathoth.handlist.ui.screens.home.TaskListState
import com.azathoth.handlist.ui.theme.HandListTheme

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
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (taskListState.isLoading) {
            Text(
                text = stringResource(R.string.loading),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (taskListState.tasks.isEmpty()) {
            Text(
                text = stringResource(R.string.no_task_item),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            var state by rememberSaveable { mutableStateOf(0) }
            val titles = listOf("Status", "Time", "Assigns")
            Column {
                TabRow(selectedTabIndex = state) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = state == index,
                            onClick = { state = index },
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )
                    }
                }
                if (state == 0) {
                    TaskViewByEnum(
                        taskListState = taskListState,
                        onTaskClick = onTaskClick,
                        keySelector = { Status.valueOf(it.status) },
                        modifier = modifier
                    )
                }
                if (state == 1) {
                    TaskViewByEnum(
                        taskListState = taskListState,
                        onTaskClick = onTaskClick,
                        keySelector = { it.endTime?.toLocalDate().toDateStatus() },
                        modifier = modifier
                    )
                }
                if (state == 2) {
                    TaskViewByAssigns(
                        taskListState = taskListState,
                        onTaskClick = onTaskClick,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
inline fun <reified E : Enum<E>> TaskViewByEnum(
    taskListState: TaskListState,
    crossinline onTaskClick: (Long) -> Unit,
    keySelector: (TaskUiState) -> E,
    modifier: Modifier = Modifier
) {
    // classify tasks by keySelector
    val tasksByStatus = taskListState.tasks.groupBy(keySelector)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(enumValues<E>()) { status ->
            val tasks = tasksByStatus[status] ?: emptyList()

            Text(
                text = "$status (${tasks.size})",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                tasks.forEach { task ->
                    TaskItem(
                        task = task,
                        onTaskClick = { onTaskClick(it.id) },
                        modifier = Modifier
                            .padding(4.dp)
                            .padding(horizontal = 8.dp)
                    )
                    Divider()

                }
            }
        }

    }
}

@Composable
fun TaskViewByAssigns(
    taskListState: TaskListState,
    onTaskClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    // classify tasks by assigns
    val tasksByAssigns = HashMap<String, MutableSet<TaskUiState>>()
    for (task in taskListState.tasks) {
        for (user in task.assigns) {
            tasksByAssigns.getOrPut(user.email) { mutableSetOf() }.add(task)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasksByAssigns.entries.toList()) { entry ->
            val tasks = entry.value
            Text(
                text = "${entry.key} (${tasks.size})",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                tasks.forEach { task ->
                    TaskItem(
                        task = task,
                        onTaskClick = { onTaskClick(it.id) },
                        modifier = Modifier
                            .padding(4.dp)
                            .padding(horizontal = 8.dp)
                    )
                    Divider()

                }
            }
        }

    }
}

@Composable
fun <K> TaskViewByKey(
    taskListState: TaskListState,
    onTaskClick: (Long) -> Unit,
    keySelector: (TaskUiState) -> K,
    modifier: Modifier = Modifier
) {
    // classify tasks by keySelector
    val tasksByStatus = taskListState.tasks.groupBy(keySelector)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasksByStatus.entries.toList()) { entry ->
            Text(
                text = entry.key.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                entry.value.forEach { task ->
                    TaskItem(
                        task = task,
                        onTaskClick = { onTaskClick(it.id) },
                        modifier = Modifier
                            .padding(4.dp)
                            .padding(horizontal = 8.dp)
                    )
                    Divider()

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
            text = task.assigns.joinToString(", ") { it.nickname },
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline
        )

        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TabRowPreview() {
    HandListTheme {
        Column {
        }
    }
}