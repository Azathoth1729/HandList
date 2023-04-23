package com.azathoth.handlist.data

import com.azathoth.handlist.common.util.toEnum
import com.azathoth.handlist.common.util.toInt
import junit.framework.TestCase.assertEquals
import org.junit.Test

class StatusTest {
    private inline fun <reified T : Enum<T>> printAllOrdinals() {
        println(enumValues<T>().map { it.ordinal })
    }

    @Test
    fun ordinalTest() {
        printAllOrdinals<Status>()
    }

    @Test
    fun toIntTest() {
        assert(Status.values().contentEquals(enumValues<Status>()))

        assert(0.toEnum<Status>() == Status.Todo)
        Status.values().forEachIndexed { idx, it ->
            assert(it.ordinal == idx)
        }

        Status.values().forEachIndexed { idx, it ->
            assert(it.toInt() == idx)
        }
    }

    @Test
    fun toStringTest() {
        assertEquals(Status.Todo.toString(), "Todo")
        assertEquals(Status.Todo, Status.valueOf("Todo"))

        println(Status.values().map { it.name })
    }


}