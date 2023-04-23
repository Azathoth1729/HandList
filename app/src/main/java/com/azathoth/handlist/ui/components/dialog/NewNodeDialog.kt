package com.azathoth.handlist.ui.components.dialog

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import com.azathoth.handlist.R
import com.azathoth.handlist.common.fs.PurePath
import com.azathoth.handlist.data.model.spacenode.SpaceNodeType
import com.azathoth.handlist.data.model.spacenode.SpaceNodeUiState
import com.azathoth.handlist.ui.theme.HandListTheme
import com.azathoth.handlist.ui.screens.spacenode.NewNodeVM


@Composable
fun NewNodeDialog(
    basePath: PurePath,
    dialogExpanded: Boolean,
    modifier: Modifier = Modifier,
    viewModel: NewNodeVM = hiltViewModel(),
    onDialogExpanded: (Boolean) -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    if (dialogExpanded) {
        AlertDialog(
            onDismissRequest = { onDialogExpanded(false) },
            title = {
                Text("Create a new file")
            },
            text = {
                NodeInputForm(
                    basePath = basePath,
                    spaceNodeUiState = viewModel.spaceNodeUiState,
                    onValueChange = viewModel::updateNode,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDialogExpanded(false)
                        onConfirm()
                    }
                ) {
                    Text(stringResource(R.string.dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDialogExpanded(false)
                    }
                ) {
                    Text(stringResource(R.string.dialog_dismiss))
                }
            },
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodeInputForm(
    basePath: PurePath,
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
            onValueChange = { onValueChange(spaceNodeUiState.copy(path = basePath / PurePath(it))) },
            label = { Text(stringResource(R.string.file_name)) }
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = spaceNodeUiState.nodetype.toString(),
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
                            onValueChange(spaceNodeUiState.copy(nodetype = SpaceNodeType.valueOf(it)))
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
                basePath = PurePath("/"),
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