package com.azathoth.handlist.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azathoth.handlist.R
import com.azathoth.handlist.data.task.Status
import com.azathoth.handlist.data.task.TaskUiState
import com.azathoth.handlist.ui.AppViewModelProvider
import com.azathoth.handlist.ui.theme.HandListTheme
import com.azathoth.handlist.ui.viewmodels.NewTaskVM
import com.azathoth.handlist.util.format
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

@Composable
fun TaskEntryBody(
    taskUiState: TaskUiState,
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
        TaskInputForm(taskUiState = taskUiState, onValueChange = onTaskValueChange)
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
    onValueChange: (TaskUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val options = Status.values().map { it.name }

    val calendarState = rememberUseCaseState()

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


    Column(
        modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = taskUiState.name,
            onValueChange = { onValueChange(taskUiState.copy(name = it)) },
            label = { Text(stringResource(R.string.task_name_req)) },
            modifier = Modifier.fillMaxWidth(),
        )
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = taskUiState.status,
                onValueChange = {},
                label = { Text(stringResource(R.string.task_status_req)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            onValueChange(taskUiState.copy(status = it))
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )

                }
            }
        }


//        OutlinedTextField(
//            value = taskUiState.path,
//            onValueChange = { onValueChange(taskUiState.copy(path = it)) },
//            label = { Text(stringResource(R.string.task_path_req)) },
//            modifier = Modifier.fillMaxWidth(),
//        )


        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${taskUiState.startTime}")
            Text(text = "${taskUiState.endTime}")

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
        val viewModel: NewTaskVM = viewModel(factory = AppViewModelProvider.Factory)
        TaskEntryBody(
            taskUiState = viewModel.taskUiState,
            onTaskValueChange = viewModel::updateUiState,
            isEdit = true,
        )
    }
}
