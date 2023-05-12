package com.azathoth.handlist.data

import com.azathoth.handlist.common.fs.PurePath
import com.azathoth.handlist.common.fs.TrieFs
import com.azathoth.handlist.data.model.task.TaskUiState
import com.azathoth.handlist.data.model.user.User

object DemoDataProvider {
    fun demoNodeTree(): TrieFs<Nothing> {
        val fs = TrieFs<Nothing>()
        fs.mkdir("Working", "Study", "Workout", "Cooking")

        fs.cd("/Working")
        fs.touch("MorningList", "AfternoonList", "NightList")

        fs.cd("/Study")
        fs.mkdir("Math", "English")
        fs.cd("Math")
        fs.touch("AnalysisList", "GeometryList", "AlgebraList")

        fs.cd("/")
        return fs
    }

    fun demoNodeTreeAbsolute(): TrieFs<Nothing> {
        val folders =
            listOf("/Working", "/Study", "/Study/Math", "/Study/English", "/Workout", "/Cooking")
        val lists = listOf(
            "/Working/MorningList",
            "/Working/AfternoonList",
            "/Study/Math/AnalysisList",
            "/Study/Math/GeometryList",
        )
        val fs = TrieFs<Nothing>()
        fs.mkdir(folders.map { PurePath(it) })
        fs.touch(lists.map { PurePath(it) })
        return fs
    }

    fun demoAllUsers(): List<User> = listOf(
        User(0, "azathoth@qq.com", "azathoth", "USER"),
        User(1, "miku@qq.com", "miku", "USER"),
        User(2, "marisa@qq.com", "marisa", "USER"),
        User(3, "a@qq.com", "a", "USER"),
        User(4, "b@qq.com", "b", "USER"),
    )

    fun demoAssignUsers(): List<User> =
        listOf(0, 2, 4).map { demoAllUsers()[it] }

    val demoAllTaskUiState = listOf(
        TaskUiState(
            name = "task1",
            status = Status.Todo.toString(),
            startTime = "2023-03-27",
            endTime = "2023-05-12"
        ),
        TaskUiState(
            name = "task2",
            status = Status.Done.toString(),
            startTime = "2023-03-28",
            endTime = "2023-04-20"
        ),
        TaskUiState(
            name = "task3",
            status = Status.InProgress.toString(),
            startTime = "2023-03-25",
        ),
        TaskUiState(
            name = "task4",
            status = Status.Todo.toString(),
            endTime = "2023-05-25"
        ),
        TaskUiState(
            name = "task5",
            status = Status.Todo.toString(),
            startTime = "2023-05-27",
        ),
        TaskUiState(
            name = "task6",
            status = Status.Done.toString(),
        ),
        TaskUiState(
            name = "task7",
            status = Status.InProgress.toString(),
            startTime = "2023-02-27",
            endTime = "2023-05-25"
        ),
    )
}