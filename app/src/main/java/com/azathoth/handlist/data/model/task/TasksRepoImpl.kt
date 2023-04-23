package com.azathoth.handlist.data.model.task

import com.azathoth.handlist.data.remote.HandListApi

class TasksRepoImpl(private val taskApi: HandListApi) : TaskRepo {
    override suspend fun getAllTasks(): List<Task> =
        taskApi.getAllTasks()

    override suspend fun getTask(task_id: Long): Task =
        taskApi.getTask(task_id)

    override suspend fun insertTask(node_id: Long, task: Task) =
        taskApi.insertTask(node_id, task)

    override suspend fun updateTask(task_id: Long, task: Task) =
        taskApi.updateTask(task_id, task)

    override suspend fun deleteTask(task_id: Long) =
        taskApi.deleteTask(task_id)

}