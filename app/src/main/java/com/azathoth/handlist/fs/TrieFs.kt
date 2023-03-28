package com.azathoth.handlist.fs

class TrieFs<T> : FileSystem<T>, FileVisitable<T> {
    // root directory
    private val root: File<T> = File(true, "/")

    // current/working directory
    private var wkDir: File<T> = root

    private fun lsAbsolute(p: PurePath): List<PurePath>? {
        val file = this.getFile(p) ?: return null
        if (file.isFile) return listOf(p)
        return file.children.map { (k, _) ->
            PurePath("$p/$k")
        }
    }

    override fun ls(p: PurePath): List<PurePath>? =
        if (!p.isAbsolute) lsAbsolute(pwd() + p) else lsAbsolute(p)

    private fun mkdirFrom(p: PurePath, dir: File<T>): Boolean {
        if (dir.isFile) return false
        var cur = dir
        var partPathStr = dir.path.toString()
        p.asList().forEach {
            partPathStr += "/$it"
            val curChild = cur.children[it]
            cur.children[it] = curChild ?: File(true, partPathStr)
            cur = cur.children[it]!!
        }
        return true
    }

    override fun mkdir(p: PurePath): Boolean =
        if (!p.isAbsolute) mkdirFrom(p, wkDir) else mkdirFrom(p, root)

    private fun touchAbsolute(p: PurePath): Boolean {
        val parentDir = this.getFile(p.parent) ?: return false
        if (parentDir.isFile || parentDir.children[p.filename.toString()] != null) return false
        parentDir.children[p.filename.toString()] = File(false, p)
        return true
    }

    override fun touch(p: PurePath): Boolean =
        if (!p.isAbsolute) touchAbsolute(pwd() + p) else touchAbsolute(p)

    private fun rmAbsolute(p: PurePath): Boolean {
        val parentDir = this.getFile(p.parent) ?: return false
        parentDir.children.remove(p.filename.toString()) ?: return false
        return true
    }

    override fun rm(p: PurePath): Boolean =
        if (!p.isAbsolute) rmAbsolute(pwd() + p) else rmAbsolute(p)

    private fun cdAbsolute(p: PurePath): Boolean {
        val file = this.getFile(p) ?: return false
        if (file.isFile) return false
        this.wkDir = file
        return true
    }

    // TODO: more efficient algorithm
    override fun cd(p: PurePath): Boolean =
        if (!p.isAbsolute) cdAbsolute(pwd() + p) else cdAbsolute(p)

    override fun pwd(): PurePath = this.wkDir.path

    fun root(): File<T> = this.root

    fun contains(p: PurePath): Boolean {
        return this.getFile(p) != null
    }

    fun writeFile(p: PurePath, data: T?): Boolean {
        val file = this.getFile(p) ?: return false
        if (file.isDir) return false
        file.data = data
        return true
    }

    fun readFile(p: PurePath): T? = this.getFile(p)?.data

    fun getFile(p: PurePath): File<T>? {
        var cur = root

        p.asList().forEach {
            val child = cur.children[it] ?: return null
            cur = child
        }
        return cur
    }

    fun getFile(pStr: String) = this.getFile(PurePath(pStr))

    fun childFile(p: File<T>, filename: String): File<T>? = p.children[filename]

    override fun accept(visitor: FileVisitor<T>) {
        this.root.accept(visitor)
    }
}

