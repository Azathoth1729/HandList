package com.azathoth.handlist.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class SpaceNodeType {
    List,
    Folder
}

data class SpaceNodeUiState(var filename: String = "", var nodetype: String = SpaceNodeType.List.toString())

class NodeVM : ViewModel() {
    var spaceNodeUiState by mutableStateOf(SpaceNodeUiState())
        private set

    val filename
        get() = spaceNodeUiState.filename

    val isFolder: Boolean
        get() = spaceNodeUiState.nodetype == SpaceNodeType.Folder.toString()

    fun updateNode(newSpaceNodeUiState: SpaceNodeUiState) {
        spaceNodeUiState = newSpaceNodeUiState
    }

}