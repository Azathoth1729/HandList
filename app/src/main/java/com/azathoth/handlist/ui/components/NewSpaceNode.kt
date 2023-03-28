package com.azathoth.handlist.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.azathoth.handlist.R
import com.azathoth.handlist.ui.theme.HandListTheme
import com.azathoth.handlist.ui.viewmodels.SpaceNodeType
import com.azathoth.handlist.ui.viewmodels.SpaceNodeUiState
import com.azathoth.handlist.ui.viewmodels.NodeVM


@Composable
fun NewNodeDialog(
    dialogExpanded: Boolean,
    modifier: Modifier = Modifier,
    viewModel: NodeVM = viewModel(),
    onDialogExpanded: (Boolean) -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    if (dialogExpanded) {
        AlertDialog(
            onDismissRequest = { onDialogExpanded(!dialogExpanded) },
            title = {
                Text(text = "Create a new file")
            },
            text = {
                NodeInputForm(
                    spaceNodeUiState = viewModel.spaceNodeUiState,
                    onValueChange = viewModel::updateNode,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDialogExpanded(!dialogExpanded)
                        onConfirm()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDialogExpanded(!dialogExpanded)
                    }
                ) {
                    Text("Dismiss")
                }
            },
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodeInputForm(
    spaceNodeUiState: SpaceNodeUiState,
    onValueChange: (SpaceNodeUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val options = SpaceNodeType.values().map { it.name }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = spaceNodeUiState.filename,
            onValueChange = { onValueChange(spaceNodeUiState.copy(filename = it)) },
            label = { Text(stringResource(R.string.file_name)) }
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = spaceNodeUiState.nodetype,
                onValueChange = {},
                label = { Text(stringResource(R.string.file_type)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            onValueChange(spaceNodeUiState.copy(nodetype = it))
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun AddNewDialogPreview() {
    HandListTheme {
        var dialogExpanded by remember { mutableStateOf(true) }
        Column {
            NewNodeDialog(
                viewModel = viewModel(),
                dialogExpanded = dialogExpanded,
                onDialogExpanded = { dialogExpanded = it }
            )
            Button(onClick = { dialogExpanded = true }) {
                Text("Regenerate dialog")
            }
        }

    }
}