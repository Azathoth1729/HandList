package com.azathoth.handlist.ui

import com.azathoth.handlist.data.model.spacenode.SpaceNodeType
import org.junit.Test

class SpaceNodeTypeTest {
    @Test
    fun test() {
        val list = SpaceNodeType.List

        assert(list == SpaceNodeType.List)
        assert(list != SpaceNodeType.Folder)

    }
}