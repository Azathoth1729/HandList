package com.azathoth.handlist.data.model.task

import com.azathoth.handlist.data.remote.HandListApi

class TasksRepoImpl(private val taskApi: HandListApi) : TaskRepo {
    override suspend fun getAllTasks(): List<Task> =
        taskApi.getAllTasks()

    override suspend fun getTask(task_id: Long): Task =
        taskApi.getTask(task_id)

    override suspend fun getAllTasksBySpaceNodeId(node_id: Long): List<Task> =
        taskApi.getAllTasksBySpaceNodeId(node_id)

    override suspend fun getAllTasksBySubPath(node_id: Long): List<Task> {
        val node = taskApi.getNode(node_id)
        return taskApi.getAllTasks().filter { it.spaceNode?.path?.startsWith(node.path) ?: false }
    }

    override suspend fun insertTask(node_id: Long, task: Task) =
        taskApi.insertTask(node_id, task)

    override suspend fun updateTask(task_id: Long, task: Task) =
        taskApi.updateTask(task_id, task)

    override suspend fun deleteTask(task_id: Long) =
        taskApi.deleteTask(task_id)

}