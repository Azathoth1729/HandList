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

class GetTasksByUserEmailUseCase @Inject constructor(
    private val taskRepo: TaskRepo
) {
    operator fun invoke(email: String?): Flow<Resource<List<TaskUiState>>> = flow {
        try {
            if (email == null) {
                emit(Resource.Error("Can't find login user"))
            } else {
                emit(Resource.Loading())
                val tasks = taskRepo.getTasksByUserEmail(email).map(Task::toUiState)
                emit(Resource.Success(tasks))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server.\nCheck your internet connection."))
        }
    }
}