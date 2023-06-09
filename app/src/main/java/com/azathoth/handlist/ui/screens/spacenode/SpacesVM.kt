package com.azathoth.handlist.ui.screens.spacenode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.common.Resource
import com.azathoth.handlist.common.fs.TrieFS
import com.azathoth.handlist.data.model.spacenode.SpaceNodeType
import com.azathoth.handlist.data.model.spacenode.SpaceNodeUiState
import com.azathoth.handlist.data.usecase.spacenode.GetSpaceNodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpacesVM @Inject constructor(
    private val usecase: GetSpaceNodesUseCase,
) : ViewModel() {
    private val fs by mutableStateOf(TrieFS<Long>())
    private var state by mutableStateOf(NodeListState())

    val root: UIFile
        get() = fs.root()

    init {
        viewModelScope.launch {
            getAllNodesFlow().collect {
                state = it
            }

            fs.writeFile(root.path, -1)

            state.data.filter { it.nodetype == SpaceNodeType.Folder }.forEach {
                fs.mkdir(it.path)
                fs.writeFile(it.path, it.id)
            }

            state.data.filter { it.nodetype == SpaceNodeType.List }.forEach {
                fs.touch(it.path)
                fs.writeFile(it.path, it.id)
            }
        }
    }


    private fun getAllNodesFlow() =
        usecase().map {
            when (it) {
                is Resource.Success -> {
                    NodeListState(data = it.data ?: emptyList())
                }

                is Resource.Error -> {
                    NodeListState(
                        error = it.message ?: "An unexpected error occurred"
                    )
                }

                is Resource.Loading -> {
                    NodeListState(isLoading = true)
                }
            }
        }

}

class NodeListState(
    val isLoading: Boolean = false,
    val data: List<SpaceNodeUiState> = emptyList(),
    val error: String = ""
)