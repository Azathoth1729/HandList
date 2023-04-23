package com.azathoth.handlist.data.model.spacenode

import com.azathoth.handlist.data.remote.HandListApi

class SpaceNodeRepoImpl(private val spaceNodeApi: HandListApi) : SpaceNodeRepo {
    override suspend fun getAllNodes(): List<SpaceNode> =
        spaceNodeApi.getAllNodes()

    override suspend fun getAllListNodes(): List<SpaceNode> =
        spaceNodeApi.getAllNodes().filter { it.nodetype == "List" }

    override suspend fun insertNode(node: SpaceNode) =
        spaceNodeApi.insertNode(node)

    override suspend fun deleteNode(node_id: Long) =
        spaceNodeApi.deleteTask(node_id)
}