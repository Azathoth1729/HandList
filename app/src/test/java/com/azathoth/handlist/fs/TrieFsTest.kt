package com.azathoth.handlist.fs

import junit.framework.TestCase.*
import org.junit.Test

class TrieFsTest {
    private fun tree(): TrieFs<String> {
        val fs = TrieFs<String>()
        fs.touch("/x2", "/x3")
        fs.mkdir("/a/b/c", "/b", "/c/a/b")
        return fs
    }

    private fun treeFromRelative(): TrieFs<String> {
        val fs = TrieFs<String>()
        fs.touch("x2", "x3")
        fs.mkdir("a", "b", "c")

        fs.cd("/c")
        fs.mkdir("a")
        fs.cd("a")
        fs.mkdir("b")
        assertEquals(PurePath("/c/a"), fs.pwd())

        fs.cd("/a")
        fs.mkdir("b")
        fs.mkdir("b/c")
        assertEquals(PurePath("/a"), fs.pwd())

        fs.cd("/")
        return fs
    }

    @Test
    fun fsMkdirTest() {
        var fs = TrieFs<String>()
        assertEquals(fs.ls(PurePath("/")), listOf<PurePath>())
        fs = tree()
        fs.mkdir("/a/b/c", "/b", "/c/a/b")
        assertEquals(
            listOf("/a", "/b", "/c", "/x2", "/x3").map { PurePath(it) }, fs.ls(PurePath("/"))
        )
        assertEquals(listOf("/c/a").map { PurePath(it) }, fs.ls(PurePath("/c")))
    }

    @Test
    fun fsMkdirTest2() {
        val fs = treeFromRelative()
        fs.mkdir("/a/b/c", "/b", "/c/a/b")
        assertEquals(
            listOf("/a", "/b", "/c", "/x2", "/x3").map { PurePath(it) }, fs.ls(PurePath("/"))
        )
        assertEquals(listOf("/c/a").map { PurePath(it) }, fs.ls(PurePath("/c")))
    }

    @Test
    fun fsTouchTest() {
        val fs = tree()
        fs.touch("/b/x1")
        assertEquals(listOf("/b/x1").map { PurePath(it) }, fs.ls(PurePath("/b")))
        assert(fs.getFile("/b/x1")!!.isFile)
        assertFalse(fs.touch("/b/x1/x1"))
        assertFalse(fs.touch("/d/x1"))
    }

    @Test
    fun fsTouchTest2() {
        val fs = treeFromRelative()
        fs.touch("/b/x1")
        assertEquals(listOf("/b/x1").map { PurePath(it) }, fs.ls(PurePath("/b")))
        assert(fs.getFile("/b/x1")!!.isFile)
        assertFalse(fs.touch("/b/x1/x1"))
        assertFalse(fs.touch("/d/x1"))

        fs.cd("a/b/c")
        fs.touch("c1", "c2")
        assertEquals(listOf("/a/b/c/c1", "/a/b/c/c2").map { PurePath(it) }, fs.ls())
    }

    @Test
    fun fsRmTest() {
        val fs = tree()
        assertFalse(fs.rm("/x6"))
        assertFalse(fs.rm("/a/b/c/d"))
        assertFalse(fs.rm("/x2/a"))

        assert(fs.rm("/c"))
        assertEquals(listOf("/a", "/b", "/x2", "/x3").map { PurePath(it) }, fs.ls("/"))
        assertEquals(listOf("/a/b").map { PurePath(it) }, fs.ls("/a"))
        assertNull(fs.ls("/c"))
        assertNull(fs.ls("/c/a"))

        assert(fs.rm("/a/b"))
        assertNull(fs.ls("/a/b"))
    }

    @Test
    fun fsRmTest2() {
        val fs = treeFromRelative()
        fs.cd("b")
        assertFalse(fs.rm("/x6"))
        assertFalse(fs.rm("x1"))

        fs.cd("/c/a")
        assert(fs.rm("b"))
        assert(fs.ls()!!.isEmpty())
        fs.cd("/")
        fs.rm("a", "c")
        assertEquals(listOf("/b", "/x2", "/x3").map { PurePath(it) }, fs.ls("/"))
        fs.rm("x2")
        assertEquals(listOf("/b", "/x3").map { PurePath(it) }, fs.ls("/"))
    }

    @Test
    fun fsLsTest() {
        val fs = treeFromRelative()
        fs.cd("a")
        fs.touch("b/c1", "b/c2")
        assertEquals(listOf("/a/b/c", "/a/b/c1", "/a/b/c2").map { PurePath(it) }, fs.ls("b"))
    }

    @Test
    fun fsWriteFileTest() {
        val fs = tree()
        fs.writeFile(PurePath("/x2"), "hello /x2")
        assertEquals("hello /x2", fs.readFile(PurePath("/x2")))
    }

    @Test
    fun fsPathTest() {
        val fs = tree()
        val file = fs.getFile(PurePath("/a/b/c"))!!
        assertEquals(PurePath("/"), fs.pwd())
        assertEquals(PurePath("/a/b/c"), file.path)
    }

    @Test
    fun fsTraverseTest() {
        val fs = tree()
        fs.writeFile(PurePath("/x2"), "hello /x2")
        fs.accept(PrintIndentedVisitor(0))
    }

    @Test
    fun fsTraverse2Test() {
        val fs = treeFromRelative()
        fs.writeFile(PurePath("/x2"), "hello /x2")
        fs.accept(PrintIndentedVisitor(0))
        println(fs.pwd())
    }

    @Test
    fun fsTraverseTest3() {
        val fs = treeFromRelative()
        fs.cd("a/b/c")
        assertEquals(PurePath("/a/b/c"), fs.pwd())
        fs.touch("c1", "c2")
        fs.accept(PrintIndentedVisitor(0))
    }
}
