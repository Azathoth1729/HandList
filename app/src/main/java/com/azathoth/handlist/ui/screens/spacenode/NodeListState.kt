package com.azathoth.handlist.ui.screens.spacenode

import com.azathoth.handlist.data.model.spacenode.SpaceNodeUiState

class NodeListState(
    val isLoading: Boolean = false,
    val data: List<SpaceNodeUiState> = emptyList(),
    val error: String = ""
)