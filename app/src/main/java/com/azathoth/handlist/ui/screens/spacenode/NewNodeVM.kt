package com.azathoth.handlist.ui.screens.spacenode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.azathoth.handlist.data.model.spacenode.SpaceNodeRepo
import com.azathoth.handlist.data.model.spacenode.SpaceNodeUiState
import com.azathoth.handlist.data.model.spacenode.isValid
import com.azathoth.handlist.data.model.spacenode.toSpaceNode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NewNodeVM @Inject constructor(private val repo: SpaceNodeRepo) : ViewModel() {
    var spaceNodeUiState by mutableStateOf(SpaceNodeUiState())
        private set

    fun updateNode(newSpaceNodeUiState: SpaceNodeUiState) {
        spaceNodeUiState = newSpaceNodeUiState
    }

    suspend fun saveNode() {
        if (spaceNodeUiState.isValid()) {
            val node = spaceNodeUiState.toSpaceNode()
            repo.insertNode(node)
        }
    }

}