package com.azathoth.handlist.fs

import org.junit.Test

class StdoutColorTest() {
    @Test
    fun printTest() {
        StdoutColor.println("red msg", StdoutColor.Red)
        println("white msg")
        StdoutColor.println("green msg", StdoutColor.Green)
    }

    @Test
    fun printTest2() {
        println("red msg".red())
        println("white msg")
        println("green msg".green())
        println("blue msg".blue())
    }
}