package com.azathoth.handlist.ui.screens.spacenode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.common.Resource
import com.azathoth.handlist.common.fs.TrieFs
import com.azathoth.handlist.data.model.spacenode.SpaceNodeType
import com.azathoth.handlist.data.usecase.spacenode.GetSpaceNodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FsVM @Inject constructor(private val usecase: GetSpaceNodesUseCase) : ViewModel() {
    private val fs by mutableStateOf(TrieFs<Nothing>())
    private var _state by mutableStateOf(NodeListState())

    private val state: NodeListState
        get() = _state

    val root: UIFile
        get() = fs.root()

    init {

        getAllNodes()
        val listNodes =
            state.data.filter { it.nodetype == SpaceNodeType.List }.map { it.path }
        val folderNodes =
            state.data.filter { it.nodetype == SpaceNodeType.Folder }.map { it.path }

        fs.mkdir(folderNodes)
        fs.touch(listNodes)

    }

    private fun getAllNodes() =
        usecase().onEach {
            _state = when (it) {
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
        }.launchIn(viewModelScope)


}