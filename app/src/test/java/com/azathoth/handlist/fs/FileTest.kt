package com.azathoth.handlist.fs

import com.azathoth.handlist.common.fs.File
import com.azathoth.handlist.common.fs.PurePath
import junit.framework.TestCase.assertEquals
import org.junit.Test

class FileTest {
    @Test
    fun constructorTest() {
        val f = File<String>(true, "/d/s")

        assert(f.isDir)
        assert(!f.isFile)
        assertEquals(PurePath("/d/s"), f.path)
    }
}