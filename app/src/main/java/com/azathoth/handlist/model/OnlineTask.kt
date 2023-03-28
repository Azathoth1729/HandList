package com.azathoth.handlist.model

import com.azathoth.handlist.data.task.Status
import java.time.LocalDate

data class OnlineTask(
    val id: Int,
    val name: String,
    val status: Status,
    val description: String,
    val createTime: LocalDate,
    val startTime: LocalDate,
    val endTime: LocalDate
)

