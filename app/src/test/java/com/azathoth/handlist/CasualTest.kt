package com.azathoth.handlist

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDateTime

/**
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CasualTest {
    @Test
    fun listTest() {
        val l = listOf(1, 2, 3, 7, 8)
        assert(listOf<Int>().dropLast(1).isEmpty())
        assert(emptyList<Int>().joinToString("/").isBlank())
    }

    @Test
    fun mutableListTest() {
        val list = mutableListOf(1, 2, 3)
        println(list.map { it + 1 }) // [1, 2, 3]

    }

    @Test
    fun stringTest() {
        println("/".split("/"))
    }

    @Test
    fun `date API Test`() {
        val currentTime = LocalDateTime.now()
        println("Current time is:$currentTime")
    }

    @Test
    fun classTest() {
        class AClass(var property: String = "") {
            val getProperty: String
                get() = property

            fun updateProperty(newProperty: String) {
                property = newProperty
            }
        }

        val a = AClass()
        assertEquals(a.getProperty, "")
        a.updateProperty("Ha")
        assertEquals(a.getProperty, "Ha")
    }

    @Test
    fun groupListOfObject() {
        data class Bucket(val title: String, val items: List<String>)

        val buckets = listOf(
            Bucket(title = "A", items = listOf("Coke", "Towel")),
            Bucket(title = "B", items = listOf("Milk", "Cookies", "Coke", "Towel")),
            Bucket(title = "C", items = listOf("Coke")),
            Bucket(title = "D", items = listOf("Cookies", "Coke")),
        )
        val map = HashMap<String, MutableSet<Bucket>>()
        for (bucket in buckets) {
            for (item in bucket.items) {
                map.getOrPut(item) { mutableSetOf() }.add(bucket)
            }
        }
        map.forEach { (k, v) ->
            println("$k = ${v.map { "Bucket(${it.title})" }}")
        }
    }
}