package com.azathoth.handlist.data.model.spacenode


enum class SpaceNodeType {
    List,
    Folder
}

class SpaceNode(
    val id: Long = 0,
    val path: String = "",
    val nodetype: String = SpaceNodeType.List.toString()
) {
    override fun toString() = path
}