package com.azathoth.handlist.common.fs

/**
   Interface of an Emulated file system
 */
interface FileSystem<T> {
    /**
     * List of paths belongs to this directory.
     *
     * If the file don't exist return null, if the file is not a directory, just return this file.
     *
     * Algorithmic complexity: O(N) + O(M)
     */
    fun ls(p: PurePath): List<PurePath>?
    fun ls(pStr: String): List<PurePath>? = this.ls(PurePath(pStr))
    // list files of current directory
    fun ls(): List<PurePath>? = this.ls(this.pwd())

    /**
     * Create the Directory(ies), if they do not already exist.
     *
     * Algorithmic complexity: O(N+M)
     */
    fun mkdir(p: PurePath): Boolean
    fun mkdir(pStr: String) = this.mkdir(PurePath(pStr))
    fun mkdir(ps: List<PurePath>) = ps.forEach { this.mkdir(it) }
    fun mkdir(vararg ps: PurePath) = ps.forEach { this.mkdir(it) }
    fun mkdir(vararg pStrs: String) = pStrs.forEach { this.mkdir(it) }

    fun touch(p: PurePath): Boolean
    fun touch(pStr: String): Boolean = this.touch(PurePath(pStr))
    fun touch(pStr: List<PurePath>) = pStr.forEach { this.touch(it) }
    fun touch(vararg pStr: PurePath) = pStr.forEach { this.touch(it) }
    fun touch(vararg pStr: String) = pStr.forEach { this.touch(it) }

    /**
     * Algorithmic complexity: O(N)
     */
    fun rm(p: PurePath): Boolean
    fun rm(pStr: String): Boolean = this.rm(PurePath(pStr))
    fun rm(ps: List<PurePath>) = ps.forEach { this.rm(it) }
    fun rm(vararg ps: PurePath) = ps.forEach { this.rm(it) }
    fun rm(vararg ps: String) = ps.forEach { this.rm(it) }

    /**
     * Algorithmic complexity: O(N)
     */
    fun cd(p: PurePath): Boolean
    fun cd(p: String): Boolean = this.cd(PurePath(p))

    fun pwd(): PurePath
}

