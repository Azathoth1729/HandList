package com.azathoth.handlist.data

import com.azathoth.handlist.common.util.toDateStatus
import com.azathoth.handlist.common.util.toLocalDate
import com.azathoth.handlist.fs.PrintIndentedVisitor
import org.junit.Test
import java.time.LocalDate

class DemoDataProviderTest {
    @Test
    fun demoFsTraverseTest() {
        val fs = DemoDataProvider.demoNodeTree()
        fs.accept(PrintIndentedVisitor(0))
    }

    @Test
    fun demoTasksGroupByTest() {
        val words = listOf("a", "abc", "ab", "def", "android")
        val byLength = words.groupBy { it.length }
        println(byLength)

        val tasks = DemoDataProvider.demoAllTaskUiState

        val statusTasks = tasks.groupBy { Status.valueOf(it.status) }
        println(statusTasks.keys)
//        println(statusTasks[Status.Todo])
        println(Status.values().forEach {
            println(it)
            println(statusTasks[it])
        })
    }


    @Test
    fun demoTasksGroupedByDate() {
        val tasksByDate = DemoDataProvider.demoAllTaskUiState.groupBy {
            it.endTime?.toLocalDate().toDateStatus()
        }
        tasksByDate.forEach { (status, tasks) ->
            println("[$status]=${tasks.map { "startTime=${it.startTime}--endTime=${it.endTime}" }}")
        }

    }
}