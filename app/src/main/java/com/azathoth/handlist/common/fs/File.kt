package com.azathoth.handlist.common.fs


class File<T>(
    val isDir: Boolean = true,
    var path: PurePath = PurePath("/"),
    var data: T? = null,
    val children: HashMap<String, File<T>> = hashMapOf()
) : FileVisitable<T> {

    val isFile: Boolean
        get() = !isDir

    constructor(
        isDir: Boolean,
        path: String,
        data: T? = null,
        children: HashMap<String, File<T>> = hashMapOf()
    ) : this(isDir, PurePath(path), data, children)

    override fun accept(visitor: FileVisitor<T>) {
        visitor.visitData(this)
        children.forEach { (_, v) ->
            val childVisitor = visitor.visitTree(v)
            v.accept(childVisitor)
        }
    }

    fun child(filename: String, isDir: Boolean): Boolean =
        if (children[filename] != null) {
            false
        } else {
            children[filename] = File(isDir, this.path + PurePath(filename))
            true
        }
}


