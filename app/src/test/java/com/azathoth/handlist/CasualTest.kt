package com.azathoth.handlist

import com.azathoth.handlist.data.Status
import com.azathoth.handlist.data.model.task.TaskUiState
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDateTime

/**
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CasualTest {
    @Test
    fun listTest() {
        val l = listOf(1, 2, 3, 7, 8)
//        println(l.subList(2, 8))
        assert(listOf<Int>().dropLast(1).isEmpty())
        assert(emptyList<Int>().joinToString("/").isBlank())
    }

    @Test
    fun mutableListTest() {
        val list = mutableListOf(1, 2, 3)
        println(list.map { it + 1 }) // [1, 2, 3]

    }

    @Test
    fun stringTest() {
        println("/".split("/"))
    }

    @Test
    fun `date API Test`() {
        val currentTime = LocalDateTime.now()
        println("Current time is:$currentTime")
    }

    @Test
    fun classTest() {
        class AClass(var property: String = "") {
            val getProperty: String
                get() = property

            fun updateProperty(newProperty: String) {
                property = newProperty
            }
        }

        val a = AClass()
        assertEquals(a.getProperty, "")
        a.updateProperty("Ha")
        assertEquals(a.getProperty, "Ha")
    }

    @Test
    fun groupByTest() {
        val words = listOf("a", "abc", "ab", "def", "abcd")
        val byLength = words.groupBy { it.length }
        println(byLength)

        val tasks = listOf(
            TaskUiState(name = "task1", status = Status.Todo.toString()),
            TaskUiState(name = "task2", status = Status.Done.toString()),
            TaskUiState(name = "task3", status = Status.InProgress.toString()),
            TaskUiState(name = "task4", status = Status.Todo.toString()),
            TaskUiState(name = "task5", status = Status.Todo.toString()),
            TaskUiState(name = "task6", status = Status.Done.toString()),
            TaskUiState(name = "task7", status = Status.InProgress.toString()),
        )
        val statusTasks = tasks.groupBy { Status.valueOf(it.status) }
        println(statusTasks.keys)
//        println(statusTasks[Status.Todo])
        println(Status.values().forEach {
            println(it)
            println(statusTasks[it])
        })
    }
}