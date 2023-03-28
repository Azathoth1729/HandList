package com.azathoth.handlist.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.azathoth.handlist.ui.theme.HandListTheme
import com.azathoth.handlist.ui.viewmodels.NodeVM


@Composable
fun FolderNode(
    name: String,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    viewModel: NodeVM,
    onExpand: (Boolean) -> Unit = {},
    onNew: () -> Unit = {},
) {
    var dialogExpanded by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(40.dp)
    )
    {
        TextButton(
            colors = ButtonDefaults.textButtonColors(
                contentColor = LocalContentColor.current,
            ),
            onClick = { onExpand(!expanded) },
        ) {
            Icon(
                imageVector = if (expanded) Icons.Outlined.ExpandMore else Icons.Outlined.ChevronRight,
                contentDescription = "expand",
            )
            Text(text = name)
        }

        TextButton(
            colors = ButtonDefaults.textButtonColors(
                contentColor = LocalContentColor.current,
            ),
            onClick = { dialogExpanded = true },
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "new file",
            )
        }
    }

    NewNodeDialog(
        dialogExpanded = dialogExpanded,
        onDialogExpanded = { dialogExpanded = it },
        viewModel = viewModel,
        onConfirm = onNew,
    )
}

@Composable
fun ListNode(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(40.dp)
    ) {
        TextButton(
            colors = ButtonDefaults.textButtonColors(
                contentColor = LocalContentColor.current,
            ),
            onClick = onClick,
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = name)
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun FolderNodePreview() {
    HandListTheme {
        var expanded by remember { mutableStateOf(false) }
        FolderNode(
            name = "Demo Folder",
            expanded = expanded,
            onExpand = { expanded = it },
            viewModel = viewModel()
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun ListNodePreview() {
    HandListTheme {
        ListNode(name = "Demo List")
    }
}