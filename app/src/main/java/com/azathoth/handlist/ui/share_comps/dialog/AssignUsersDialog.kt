package com.azathoth.handlist.ui.share_comps.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.azathoth.handlist.R
import com.azathoth.handlist.data.DemoDataProvider
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
                val allUsers = DemoDataProvider.demoAllUsers().toMutableStateList()
                val assignUsers = DemoDataProvider.demoAssignUsers().toMutableStateList()
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

