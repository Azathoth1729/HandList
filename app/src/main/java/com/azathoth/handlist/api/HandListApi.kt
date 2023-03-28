package com.azathoth.handlist.api

import com.azathoth.handlist.model.OnlineTask
import retrofit2.http.GET

interface HandListApi {
    @GET("tasks")
    suspend fun getAllTasks(): List<OnlineTask>

}