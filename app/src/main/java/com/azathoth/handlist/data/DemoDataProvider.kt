package com.azathoth.handlist.data

import com.azathoth.handlist.common.fs.PurePath
import com.azathoth.handlist.common.fs.TrieFS
import com.azathoth.handlist.data.model.task.TaskUiState
import com.azathoth.handlist.data.model.user.User

object DemoDataProvider {
    val demoNodeTree: TrieFS<Nothing> = TrieFS<Nothing>().apply {
        this.mkdir("Working", "Study", "Workout", "Cooking")

        this.cd("/Working")
        this.touch("MorningList", "AfternoonList", "NightList")

        this.cd("/Study")
        this.mkdir("Math", "English")
        this.cd("Math")
        this.touch("AnalysisList", "GeometryList", "AlgebraList")

        this.cd("/")
    }

    val demoNodeTreeAbsolute: TrieFS<Nothing> = TrieFS<Nothing>().apply {
        val folders =
            listOf("/Working", "/Study", "/Study/Math", "/Study/English", "/Workout", "/Cooking")
        val lists = listOf(
            "/Working/MorningList",
            "/Working/AfternoonList",
            "/Study/Math/AnalysisList",
            "/Study/Math/GeometryList",
        )
        this.mkdir(folders.map { PurePath(it) })
        this.touch(lists.map { PurePath(it) })
    }


    val demoAllUsers: List<User> = listOf(
        User(0, "azathoth@qq.com", "azathoth", "USER"),
        User(1, "miku@qq.com", "miku", "USER"),
        User(2, "marisa@qq.com", "marisa", "USER"),
        User(3, "a@qq.com", "a", "USER"),
        User(4, "b@qq.com", "b", "USER"),
    )

    val demoAssignUsers: List<User> =
        listOf(0, 2, 4).map { demoAllUsers[it] }

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