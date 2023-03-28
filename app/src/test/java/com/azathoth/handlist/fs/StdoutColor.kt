package com.azathoth.handlist.fs

class StdoutColor(private val code: String) {
    companion object {
        val Red
            get() = StdoutColor("\u001b[31m")
        val Green
            get() = StdoutColor("\u001b[32m")
        val Black
            get() = StdoutColor("\u001b[30m")
        val Yellow
            get() = StdoutColor("\u001b[33m")
        val Blue
            get() = StdoutColor("\u001b[34m")

        private fun reset() = StdoutColor("\u001b[0m")

        fun color(s: String, color: StdoutColor): String =
            reset().code + color.code + s + reset().code

        fun print(message: Any?, color: StdoutColor): Unit =
            kotlin.io.print(color("$message", color))

        fun println(message: Any?, color: StdoutColor): Unit =
            kotlin.io.println(color("$message", color))

    }

    override fun toString(): String = code
}