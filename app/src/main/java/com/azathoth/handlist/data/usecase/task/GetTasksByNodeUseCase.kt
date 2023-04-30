package com.azathoth.handlist.data.usecase.task

import com.azathoth.handlist.common.Resource
import com.azathoth.handlist.data.model.task.Task
import com.azathoth.handlist.data.model.task.TaskRepo
import com.azathoth.handlist.data.model.task.TaskUiState
import com.azathoth.handlist.data.model.task.toUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTasksByNodeUseCase @Inject constructor(
    private val taskRepo: TaskRepo
) {
    operator fun invoke(nodeId: Long): Flow<Resource<List<TaskUiState>>> = flow {
        try {
            emit(Resource.Loading())
            val tasks = taskRepo.getAllTasksBySpaceNodeId(nodeId).map(Task::toUiState)
            emit(Resource.Success(tasks))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}