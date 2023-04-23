package com.azathoth.handlist.data.model.spacenode

import com.azathoth.handlist.common.fs.PurePath

data class SpaceNodeUiState(
    var id: Long = 0,
    var path: PurePath = PurePath(""),
    var nodetype: SpaceNodeType = SpaceNodeType.List
) {
    val filename: String
        get() = path.filename.toString()
}

fun SpaceNodeUiState.toSpaceNode() = SpaceNode(
    id = id,
    path = path.toString(),
    nodetype = nodetype.toString()
)

fun SpaceNode.toUiState() = SpaceNodeUiState(
    id = id,
    path = PurePath(path),
    nodetype = SpaceNodeType.valueOf(nodetype)
)

fun SpaceNodeUiState.isValid(): Boolean =
    this.filename.isNotBlank()