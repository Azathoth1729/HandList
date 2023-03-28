package com.azathoth.handlist.ui

import com.azathoth.handlist.ui.viewmodels.SpaceNodeType
import org.junit.Test

class SpaceNodeTypeTest {
    @Test
    fun test() {
        val list = SpaceNodeType.List

        assert(list == SpaceNodeType.List)
        assert(list != SpaceNodeType.Folder)

    }
}