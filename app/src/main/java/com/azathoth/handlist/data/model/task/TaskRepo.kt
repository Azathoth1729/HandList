package com.azathoth.handlist.data.model.task

interface TaskRepo {
    suspend fun getAllTasks(): List<Task>
    suspend fun getTask(task_id: Long): Task
    suspend fun getAllTasksBySpaceNodeId(node_id: Long): List<Task>
    suspend fun getAllTasksBySubPath(node_id: Long): List<Task>
    suspend fun insertTask(node_id: Long, task: Task)
    suspend fun updateTask(task_id: Long, task: Task)
    suspend fun deleteTask(task_id: Long)
}