package com.azathoth.handlist.data

import com.azathoth.handlist.fs.PrintIndentedVisitor
import org.junit.Test

class DemoDataTest {
    @Test
    fun demoFsTraverseTest() {
        val fs = DemoData.demoNodeTree()
        fs.accept(PrintIndentedVisitor(0))
    }
}