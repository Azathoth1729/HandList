package com.azathoth.handlist.data.model.task

import androidx.compose.runtime.mutableStateListOf
import com.azathoth.handlist.common.util.format
import com.azathoth.handlist.data.Status
import com.azathoth.handlist.data.model.spacenode.SpaceNode
import com.azathoth.handlist.data.model.user.User
import java.time.LocalDate

data class TaskUiState(
    var id: Long = 0,
    var name: String = "",
    var status: String = Status.Todo.toString(),
    var description: String = "",
    val createTime: String = LocalDate.now().format(),
    var startTime: String? = null,
    var endTime: String? = null,
    val spaceNode: SpaceNode? = null,
    var assigns: MutableList<User> = mutableStateListOf()
)

fun TaskUiState.toTask(): Task = Task(
    id = id,
    name = name,
    status = status,
    description = description,
    createTime = createTime,
    startTime = startTime,
    endTime = endTime,
    spaceNode = spaceNode,
    assigns = assigns
)

fun Task.toUiState(): TaskUiState = TaskUiState(
    id = id,
    name = name,
    status = status,
    description = description,
    createTime = createTime,
    startTime = startTime,
    endTime = endTime,
    spaceNode = spaceNode,
    assigns = assigns
)


fun TaskUiState.isValid(): Boolean =
    this.name.isNotBlank()