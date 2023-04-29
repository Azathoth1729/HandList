package com.azathoth.handlist.data

import com.azathoth.handlist.common.fs.PurePath
import com.azathoth.handlist.common.fs.TrieFs
import com.azathoth.handlist.data.model.user.User

object DemoData {
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
}