package com.azathoth.handlist.data.task


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.azathoth.handlist.util.format

import java.time.LocalDateTime


@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var status: String = Status.Todo.toString(),
    var description: String = "",
    val createTime: String = LocalDateTime.now().format(),
    var startTime: String? = null,
    var endTime: String? = null,
    var path: String? = null,
)
