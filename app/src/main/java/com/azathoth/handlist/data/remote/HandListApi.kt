package com.azathoth.handlist.data.remote

import com.azathoth.handlist.data.model.spacenode.SpaceNode
import com.azathoth.handlist.data.model.task.Task
import com.azathoth.handlist.data.model.user.User
import com.azathoth.handlist.data.model.user.auth.AuthRequest
import com.azathoth.handlist.data.model.user.auth.RegisterRequest
import com.azathoth.handlist.data.model.user.auth.TokenResponse
import retrofit2.http.*

interface HandListApi {
    /**
     * Retrieve all the tasks from the the given data source.
     */
    @GET("tasks")
    suspend fun getAllTasks(): List<Task>

    /**
     * Retrieve an task from the given data source that matches with the [taskId].
     */
    @GET("tasks/{id}")
    suspend fun getTask(@Path("id") taskId: Long): Task

    /**
     * Retrieve all tasks from a spacenode that matches with the [nodeId].
     */
    @GET("spacenodes/{node_id}/tasks")
    suspend fun getAllTasksBySpaceNodeId(@Path("node_id") nodeId: Long): List<Task>

    /**
     * Insert task in the data source
     */
    @POST("spacenodes/{node_id}/tasks")
    suspend fun insertTask(@Path("node_id") nodeId: Long, @Body task: Task)

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

    @POST("auth/authenticate")
    suspend fun signIn(@Body request: AuthRequest): TokenResponse

    @POST("auth/register")
    suspend fun signUp(@Body request: RegisterRequest): TokenResponse
}