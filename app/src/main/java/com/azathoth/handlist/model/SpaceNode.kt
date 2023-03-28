package com.azathoth.handlist.model

import com.azathoth.handlist.ui.viewmodels.SpaceNodeType

data class SpaceNode(
    val id: Int,
    val path: String,
    val nodetype: SpaceNodeType
)