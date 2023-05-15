package com.azathoth.handlist.ui.screens.spacenode.comps


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azathoth.handlist.common.fs.PurePath
import com.azathoth.handlist.ui.screens.spacenode.NewNodeVM
import com.azathoth.handlist.ui.theme.HandListTheme


@Composable
fun FolderNode(
    name: String,
    basePath: PurePath,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    viewModel: NewNodeVM,
    onExpand: (Boolean) -> Unit = {},
    onNodeDetail: () -> Unit = {},
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

        Row {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "detail of a folder",
                modifier = Modifier.clickable { onNodeDetail() }
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "new file",
                modifier = Modifier.clickable { dialogExpanded = true }
            )
        }

    }

    NewNodeDialog(
        basePath = basePath,
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
            basePath = PurePath("/"),
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