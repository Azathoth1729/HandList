package com.azathoth.handlist.ui.screens.spacenode

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.common.fs.File
import com.azathoth.handlist.ui.screens.spacenode.comps.FolderNode
import com.azathoth.handlist.ui.screens.spacenode.comps.ListNode
import com.azathoth.handlist.ui.share_comps.MainTopBar
import com.azathoth.handlist.ui.theme.HandListTheme
import kotlinx.coroutines.launch

typealias UIFile = File<Long>
typealias UIFileMap = HashMap<String, File<Long>>


@Composable
fun SpacesScreen(
    fsVM: FsVM = hiltViewModel(),
    navigateToTasksOfNode: (Long) -> Unit = {},
    navigateToProfile: () -> Unit = { },
) {
    val node = fsVM.root
    val expandedItems = remember { mutableStateListOf<UIFile>() }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTopBar(navigateToProfile = navigateToProfile)
        LazyColumn {
            node(
                node = node,
                isExpanded = {
                    expandedItems.contains(it)
                },
                toggleExpanded = {
                    if (expandedItems.contains(it)) {
                        expandedItems.remove(it)
                    } else {
                        expandedItems.add(it)
                    }
                },
                onListClick = navigateToTasksOfNode,
            )
        }
    }

}

fun LazyListScope.node(
    node: UIFile,
    isExpanded: (UIFile) -> Boolean,
    toggleExpanded: (UIFile) -> Unit,
    onListClick: (Long) -> Unit,
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
                    expanded = isExpanded(node),
                    onExpand = { toggleExpanded(node) },
                    onNodeDetail = {
                        node.data?.let { onListClick(it) }
                        Log.i("onNodeDetail", "${node.path}: ${node.data}")
                    },
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
                        node.data?.let { onListClick(it) }
                    },
                )
            }
        }
    }
    if (isExpanded(node)) {
        nodes(
            node.children,
            isExpanded = isExpanded,
            toggleExpanded = toggleExpanded,
            onListClick = onListClick,
        )
    }
}

fun LazyListScope.nodes(
    nodes: UIFileMap,
    isExpanded: (UIFile) -> Boolean,
    toggleExpanded: (UIFile) -> Unit,
    onListClick: (Long) -> Unit,
) {
    nodes.forEach { (_, v) ->
        node(
            node = v,
            isExpanded = isExpanded,
            toggleExpanded = toggleExpanded,
            onListClick = onListClick,
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
