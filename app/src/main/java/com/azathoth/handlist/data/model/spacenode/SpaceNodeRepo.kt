package com.azathoth.handlist.data.model.spacenode

interface SpaceNodeRepo {
    suspend fun getAllNodes(): List<SpaceNode>
    suspend fun getAllListNodes(): List<SpaceNode>
    suspend fun insertNode(node: SpaceNode)
    suspend fun deleteNode(node_id: Long)
}