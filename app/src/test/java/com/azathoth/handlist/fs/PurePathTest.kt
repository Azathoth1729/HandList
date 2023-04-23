package com.azathoth.handlist.fs

import com.azathoth.handlist.common.fs.PurePath
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class PurePathTest {
    private fun javaPathBasicInfo(javaPath: java.nio.file.Path) {
        println("javaPath.toString()=${javaPath}")
        println("javaPath.root=${javaPath.root}")
        println("javaPath.fileName=${javaPath.fileName}")
        println("javaPath.isAbsolute=${javaPath.isAbsolute}")
        println("javaPath.nameCount=${javaPath.nameCount}")
        println("javaPath.parent=${javaPath.parent}")
        println("javaPath.toAbsolutePath()=${javaPath.toAbsolutePath()}")
    }

    private fun pathCompareBasic(javaPath: java.nio.file.Path, purePath: PurePath) {
        if (javaPath.root != null) {
            assertEquals(javaPath.root.toString(), purePath.root.toString())
        } else {
            assertNull(purePath.root)
        }
        assertEquals(javaPath.fileName.toString(), purePath.filename.toString())
        assertEquals(javaPath.isAbsolute, purePath.isAbsolute)

        if (javaPath.toString() != "") {
            assertEquals(javaPath.nameCount, purePath.nameCount)
        } else {
            assertEquals(purePath.nameCount, 0)
        }

        if (javaPath.parent != null) {
            assertEquals(javaPath.parent.toString(), purePath.parent.toString())
        } else {
            assertNull(purePath.parent)
        }
        (0 until purePath.nameCount).forEach {
            assertEquals(javaPath.getName(it).toString(), purePath.getName(it).toString())
        }
    }

    @Test
    fun pathCompareTest() {
        val p = "/home/joe/1/foo"
        val javaPath = java.nio.file.Paths.get(p)
        val purePath = PurePath(p)
        pathCompareBasic(javaPath, purePath)

        assertEquals(javaPath.subpath(0, 2).toString(), purePath.subPath(0, 2).toString())
    }

    @Test
    fun pathCompareTest2() {
        val p = "home//joe/../1/./foo"
        val javaPath = java.nio.file.Paths.get(p)
        val purePath = PurePath(p)

        pathCompareBasic(javaPath, purePath)

        assertEquals(javaPath.subpath(1, 3).toString(), purePath.subPath(1, 3).toString())
    }

    @Test
    fun pathCompareTest3() {
        val p = ""
        val javaPath = java.nio.file.Paths.get(p)
        val purePath = PurePath(p)

        pathCompareBasic(javaPath, purePath)
    }

    @Test
    fun purePathTest() {
        var purePath = PurePath("/home/joe/1/foo")
        println(purePath)
        println("path.root=${purePath.root}")
        println(purePath.filename)
        println(purePath.isAbsolute)
        println(purePath.getName(0))
        println(purePath.nameCount)
        println("path.subPath(0, 2)=${purePath.subPath(0, 2)}")
        println(purePath.parent)

        purePath = PurePath("")
        assertNull(purePath.root)
        assertNull(purePath.parent)
    }

    @Test
    fun purePathJoinTest() {
        val root = PurePath("/")
        val empty = PurePath("")
        assertEquals(root.resolve(empty), root)
        assertEquals(root + root, root)
        assertEquals(empty + empty, empty)
        assertEquals(PurePath("a/v") + PurePath(""), PurePath("a/v"))
        assertEquals(PurePath("a/v") + PurePath("/home/alb"), PurePath("/home/alb"))
        assertEquals(PurePath("/home/joe/foo") + PurePath("bar"), PurePath("/home/joe/foo/bar"))
        assertEquals(PurePath("  a/  v") + PurePath("  /b  /f"), PurePath("a/v/b/f"))
    }


    @Test
    fun pathIterTest() {
        val p = PurePath("/home/jack/bin/ok")
        var partPathStr = ""
        p.asList().forEach {
            partPathStr += "/$it"
            println(partPathStr)
        }
    }

    @Test
    fun javaPathTest() {
        val p = "/home/joe/1/foo"
        val javaPath = java.nio.file.Paths.get(p)

        javaPathBasicInfo(javaPath)

        println("path.subpath(0, 2)=${javaPath.subpath(0, 2)}")

        println("javaPath.getName(0)=${javaPath.getName(0)}")
        println("javaPath.getName(1)=${javaPath.getName(1)}")

        println("javaPath.getName(1).isAbsolute=${javaPath.getName(1).isAbsolute}")
        println("javaPath.getName(1).root=${javaPath.getName(1).root}")
    }

    @Test
    fun javaPathTest2() {
        val p = ""
        var javaPath = java.nio.file.Paths.get(p)
        assert(javaPath.fileName.toString() == "")
        assertNull(javaPath.root)
        javaPathBasicInfo(javaPath)
        println()

        javaPath = java.nio.file.Paths.get("/")
        javaPathBasicInfo(javaPath)
        println()

        javaPath = java.nio.file.Paths.get("   /")
        javaPathBasicInfo(javaPath)
        println()

        javaPath = java.nio.file.Paths.get("   ")
        javaPathBasicInfo(javaPath)
        println()

        javaPath = java.nio.file.Paths.get(" /d /a ")
        javaPathBasicInfo(javaPath)
    }

    @Test
    fun javaPathTest3() {
        val p = "home/wil/ope"
        val javaPath = java.nio.file.Paths.get(p)
        javaPathBasicInfo(javaPath)
    }

    @Test
    fun javaPathTest4() {
        val p = "home/wil/.././ope"
        var javaPath = java.nio.file.Paths.get(p)
        javaPathBasicInfo(javaPath)
        javaPath = javaPath.normalize()
        println()
        javaPathBasicInfo(javaPath)
    }

    @Test
    fun javaPathTest5() {
        val p = "home/wil/ope"
        val javaPath = java.nio.file.Paths.get(p)
        println(javaPath.resolve(java.nio.file.Paths.get(p)))
    }
}
