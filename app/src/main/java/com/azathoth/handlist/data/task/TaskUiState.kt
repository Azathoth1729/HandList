package com.azathoth.handlist.data.task

import com.azathoth.handlist.util.format
import java.time.LocalDateTime

data class TaskUiState(
    val id: Int = 0,
    var name: String = "",
    var status: String = Status.Todo.toString(),
    var description: String = "",
    val createTime: String = LocalDateTime.now().format(),
    var startTime: String? = null,
    var endTime: String? = null,
    var path: String = "",
)

fun TaskUiState.toTask(): Task = Task(
    id = id,
    name = name,
    status = status,
    description = description,
    createTime = createTime,
    startTime = startTime,
    endTime = endTime,
    path = path,
)

fun Task.toUiState(): TaskUiState = TaskUiState(
    id = id,
    name = name,
    status = status,
    description = description,
    createTime = createTime,
    startTime = startTime,
    endTime = endTime,
    path = path ?: "",
)


fun TaskUiState.isValid(): Boolean =
    this.name.isNotBlank()