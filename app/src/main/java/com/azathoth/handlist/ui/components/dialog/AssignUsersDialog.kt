package com.azathoth.handlist.ui.components.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.azathoth.handlist.R
import com.azathoth.handlist.data.DemoData
import com.azathoth.handlist.ui.theme.HandListTheme

@Composable
fun AssignUsersDialog(
    dialogExpanded: Boolean,
    modifier: Modifier = Modifier,
    onDialogExpanded: (Boolean) -> Unit = {},
    onConfirm: () -> Unit = {},
    content: @Composable (() -> Unit)? = null,
) {
    if (dialogExpanded) {
        AlertDialog(
            onDismissRequest = {
                onDialogExpanded(false)
            },
            text = content,
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


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun AssignUsersDialogPreview() {
    HandListTheme {
        var dialogExpanded by remember { mutableStateOf(true) }
        Column {
            AssignUsersDialog(
                dialogExpanded = dialogExpanded,
                onDialogExpanded = { dialogExpanded = it }
            ) {
                val allUsers = DemoData.demoAllUsers().toMutableStateList()
                val assignUsers = DemoData.demoAssignUsers().toMutableStateList()
                UserList(
                    allUsers = allUsers,
                    assignUsers = assignUsers,
                    toggleUser = { user, isContain ->
                        if (isContain) {
                            assignUsers.remove(user)
                        } else {
                            assignUsers.add(user)
                        }
                    }
                )
            }
            Button(onClick = { dialogExpanded = true }) {
                Text("Regenerate dialog")
            }
        }

    }
}

