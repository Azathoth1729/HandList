package com.azathoth.handlist.fs

class PrintIndentedVisitor<T>(private var indent: Int) : FileVisitor<T> {
    override fun visitTree(tree: File<T>): Visitor<T, File<T>> = PrintIndentedVisitor(indent + 2)

    override fun visitData(cur: File<T>) {
        if (indent > 0) {
            print("├")
        }

        repeat(indent) {
            print("-")
        }

        val filepath = if (cur.isDir) cur.path.toString().blue() else cur.path.toString()
        val filedata = "${cur.data}".yellow()
        val depth = "${indent / 2}".green()
        val msg = "$filepath: {data=$filedata, depth=$depth}"
        println(msg)
    }

}