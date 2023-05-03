package com.azathoth.handlist.ui.share_comps

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.azathoth.handlist.data.model.task.TaskUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownMenu(
    taskUiState: TaskUiState,
    onValueChange: (TaskUiState, T) -> Unit,
    value: String,
    @StringRes labelTextRes: Int,
    options: List<T>,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = value,
            onValueChange = { },
            label = { Text(stringResource(labelTextRes)) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it.toString()) },
                    onClick = {
                        onValueChange(taskUiState, it)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

