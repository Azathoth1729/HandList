package com.azathoth.handlist.data.remote

import com.azathoth.handlist.data.model.spacenode.SpaceNode
import com.azathoth.handlist.data.model.task.Task
import com.azathoth.handlist.data.model.user.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HandListApi {
    /**
     * Retrieve all the tasks from the the given data source.
     */
    @GET("tasks")
    suspend fun getAllTasks(): List<Task>

    /**
     * Retrieve an task from the given data source that matches with the [task_id].
     */
    @GET("tasks/{id}")
    suspend fun getTask(@Path("id") task_id: Long): Task

    /**
     * Insert task in the data source
     */
    @POST("spacenodes/{id}/tasks")
    suspend fun insertTask(@Path("id") node_id: Long, @Body task: Task)

    /**
     * Update task in the data source
     */
    @PUT("tasks/{id}")
    suspend fun updateTask(@Path("id") task_id: Long, @Body task: Task)

    /**
     * Delete task from the data source
     */
    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") task_id: Long)

    @GET("spacenodes")
    suspend fun getAllNodes(): List<SpaceNode>

    @GET("spacenodes/{id}")
    suspend fun getNode(@Path("id") node_id: Long): SpaceNode

    @POST("spacenodes")
    suspend fun insertNode(@Body node: SpaceNode)

    @PUT("spacenodes/{id}")
    suspend fun updateNode(@Path("id") node_id: Long, @Body node: SpaceNode)

    @DELETE("spacenodes/{id}")
    suspend fun deleteNode(@Path("id") node_id: Long)


    @GET("users")
    suspend fun getAllUsers(): List<User>

}