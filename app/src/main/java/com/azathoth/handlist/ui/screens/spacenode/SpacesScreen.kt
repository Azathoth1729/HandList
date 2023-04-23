package com.azathoth.handlist.ui.screens.spacenode

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.common.fs.File
import com.azathoth.handlist.ui.components.FolderNode
import com.azathoth.handlist.ui.components.ListNode
import com.azathoth.handlist.ui.theme.HandListTheme
import kotlinx.coroutines.launch

typealias UIFile = File<Nothing>
typealias UIFileMap = HashMap<String, File<Nothing>>


@Composable
fun SpacesScreen(fsVM: FsVM = hiltViewModel()) {
    val node = fsVM.root
    val expandedItems = remember { mutableStateListOf<UIFile>() }
    LazyColumn {
        node(
            node = node,
            expanded = {
                expandedItems.contains(it)
            },
            onExpand = {
                if (expandedItems.contains(it)) {
                    expandedItems.remove(it)
                } else {
                    expandedItems.add(it)
                }
            },
        )
    }
}

fun LazyListScope.nodes(
    nodes: UIFileMap,
    isExpanded: (UIFile) -> Boolean,
    toggleExpanded: (UIFile) -> Unit,
) {
    nodes.forEach { (_, v) ->
        node(
            node = v,
            expanded = isExpanded,
            onExpand = toggleExpanded,
        )
    }
}

fun LazyListScope.node(
    node: UIFile,
    expanded: (UIFile) -> Boolean,
    onExpand: (UIFile) -> Unit,
) {
    item {
        val filename = node.path.filename.toString()
        val depth = node.path.asList().size
        val newNodeVM: NewNodeVM = hiltViewModel()
        val coroutineScope = rememberCoroutineScope()

        Row {
            Spacer(modifier = Modifier.width((10 * depth).dp))
            if (node.isDir) {
                FolderNode(
                    name = filename,
                    basePath = node.path,
                    viewModel = newNodeVM,
                    expanded = expanded(node),
                    onExpand = { onExpand(node) },
                    onNew = {
                        coroutineScope.launch {
                            newNodeVM.saveNode()
                        }
                    }
                )
            } else {
                ListNode(
                    name = filename,
                    onClick = {
//                        onExpand(node)
                    },
                )
            }
        }


    }
    if (expanded(node)) {
        nodes(
            node.children,
            isExpanded = expanded,
            toggleExpanded = onExpand,
        )
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun TreeViewPreview() {
    HandListTheme {
        SpacesScreen()
    }
}