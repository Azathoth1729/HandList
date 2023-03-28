package com.azathoth.handlist.data

import com.azathoth.handlist.fs.PurePath
import com.azathoth.handlist.fs.TrieFs

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
}