package com.azathoth.handlist.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.R
import com.azathoth.handlist.data.Status
import com.azathoth.handlist.data.model.spacenode.SpaceNode
import com.azathoth.handlist.data.model.task.TaskUiState
import com.azathoth.handlist.data.model.user.User
import com.azathoth.handlist.ui.components.dialog.AssignUsersDialog
import com.azathoth.handlist.ui.components.dialog.UserList
import com.azathoth.handlist.ui.theme.HandListTheme
import com.azathoth.handlist.ui.screens.task.NewTaskVM
import com.azathoth.handlist.common.util.format
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

@Composable
fun TaskEntryBody(
    taskUiState: TaskUiState,
    listNodes: List<SpaceNode>,
    users: List<User>,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onTaskValueChange: (TaskUiState) -> Unit = {},
    isEdit: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TaskInputForm(
            taskUiState = taskUiState,
            onValueChange = onTaskValueChange,
            listNodes = listNodes,
            users = users,
        )

        Spacer(modifier = modifier.height(16.dp))

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(
                onClick = onSaveClick, modifier = Modifier.width(100.dp)
            ) {
                Text(stringResource(R.string.save_action))
            }
            if (isEdit) {
                Spacer(modifier = modifier.width(40.dp))
                Button(
                    onClick = onDeleteClick,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(stringResource(R.string.del_action))
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputForm(
    taskUiState: TaskUiState,
    listNodes: List<SpaceNode>,
    users: List<User>,
    onValueChange: (TaskUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val calendarState = rememberUseCaseState()
    var dialogExpanded by remember { mutableStateOf(false) }

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Period { startDate, endDate ->
            onValueChange(
                taskUiState.copy(
                    startTime = startDate.format(),
                    endTime = endDate.format()
                )
            )
        },
    )

    AssignUsersDialog(
        dialogExpanded = dialogExpanded,
        onDialogExpanded = { dialogExpanded = it },
        onConfirm = {}
    ) {
        UserList(
            allUsers = users,
            assignUsers = taskUiState.assigns,
            toggleUser = { user, isContain ->
                if (isContain) {
                    taskUiState.assigns.remove(user)
                } else {
                    taskUiState.assigns.add(user)
                }
            }
        )
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = taskUiState.name,
            onValueChange = { onValueChange(taskUiState.copy(name = it)) },
            label = { Text(stringResource(R.string.task_name_req)) },
            modifier = Modifier.fillMaxWidth(),
        )

        DropdownMenu(
            taskUiState = taskUiState,
            onValueChange = { taskUiState, value ->
                onValueChange(taskUiState.copy(spaceNode = value))
            },
            value = taskUiState.spaceNode?.path ?: "",
            labelTextRes = R.string.task_location_req,
            options = listNodes,
        )

        DropdownMenu(
            taskUiState = taskUiState,
            onValueChange = { taskUiState, value ->
                onValueChange(taskUiState.copy(status = value))
            },
            value = taskUiState.status,
            labelTextRes = R.string.task_status_req,
            options = Status.values().map { it.name },
        )

        Row(verticalAlignment = Alignment.CenterVertically) {

            taskUiState.assigns.map {
                Text(it.nickname.substring(0..3))
                Spacer(modifier = Modifier.width(8.dp))
            }

            Spacer(Modifier.weight(1f))

            Button(onClick = { dialogExpanded = true }) {
                Text(stringResource(R.string.assignees))
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${taskUiState.startTime}")
            Text(text = "${taskUiState.endTime}")

            Spacer(Modifier.weight(1f))

            Button(onClick = { calendarState.show() }) {
                Text(stringResource(R.string.task_add_date_req))
            }
        }



        OutlinedTextField(
            value = taskUiState.description,
            onValueChange = { onValueChange(taskUiState.copy(description = it)) },
            label = { Text(stringResource(R.string.task_desc_req)) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewTaskPreview() {
    HandListTheme {
        val viewModel: NewTaskVM = hiltViewModel()
        TaskEntryBody(
            taskUiState = viewModel.taskUiState,
            listNodes = listOf(),
            users = listOf(),
            onTaskValueChange = viewModel::updateUiState,
            isEdit = true,
        )
    }
}
