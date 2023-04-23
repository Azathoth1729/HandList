package com.azathoth.handlist.data.model.task

import com.azathoth.handlist.data.model.user.User
import com.azathoth.handlist.data.model.spacenode.SpaceNode

data class Task(
    val id: Long,
    val name: String,
    val status: String,
    val description: String,
    val createTime: String,
    val startTime: String?,
    val endTime: String?,
    val spaceNode: SpaceNode?,
    val assigns: MutableList<User>
)