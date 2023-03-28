package com.azathoth.handlist.fs


class File<T>(val isDir: Boolean) : FileVisitable<T> {
    val isFile: Boolean = !isDir
    var data: T? = null
    var path: PurePath = PurePath("")

    val children: HashMap<String, File<T>> = hashMapOf()

    constructor() : this(true)

    constructor(isDir: Boolean, path: PurePath) : this(isDir) {
        this.path = path
    }

    constructor(isDir: Boolean, path: String) : this(isDir) {
        this.path = PurePath(path)
    }

    override fun accept(visitor: FileVisitor<T>) {
        visitor.visitData(this)
        children.forEach { (_, v) ->
            val childVisitor = visitor.visitTree(v)
            v.accept(childVisitor)
        }
    }

    fun child(filename: String, isDir: Boolean): Boolean {
        if (children[filename] != null) {
            return false
        }
        children[filename] = File(isDir, this.path + PurePath(filename))
        return true
    }
}


