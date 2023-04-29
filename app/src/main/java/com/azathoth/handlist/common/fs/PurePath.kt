package com.azathoth.handlist.common.fs


/** Pure path objects provide path-handling operations which don’t actually access a filesystem

 */
class PurePath(pathStr: String) {
    private val pathComponents: List<String> =
        pathStr.filterNot { it.isWhitespace() }.split("/").filter { it != "" }

    val isAbsolute: Boolean = pathStr.startsWith("/")

    private val prefix: String
        get() = if (isAbsolute) "/" else ""

    private val pathStr: String
        get() = prefix + pathComponents.joinToString("/")

    val nameCount: Int
        get() = pathComponents.size

    val filename: PurePath
        get() = if (pathComponents.isEmpty()) PurePath("") else PurePath(pathComponents.last())

    val root: PurePath?
        get() = if (isAbsolute) PurePath(prefix) else null

    val parent: PurePath?
        get() =
            if (!isAbsolute && pathComponents.size <= 1) null
            else PurePath(prefix + pathComponents.dropLast(1).joinToString("/"))

    fun getName(index: Int): PurePath = PurePath(pathComponents[index])

    /**
     * Resolve the given path against this path.
     *
     * If the [other] parameter is an [isAbsolute]
     * path then this method trivially returns [other]. If [other]
     * is an <i>empty path</i> then this method trivially returns this path.
     * Otherwise this method considers this path to be a directory and resolves
     * the given path against this path.
     *
     * @param   other the path to resolve against this path

     * @return  the resulting path
     */
    fun resolve(other: PurePath): PurePath =
        if (other.isAbsolute) other
        else if (other.pathStr == "") this
        else PurePath(this.pathStr + "/" + other.pathStr)

    fun resolve(other: String): PurePath = this.resolve(PurePath(other))

    fun subPath(fromIndex: Int, toIndex: Int): PurePath =
        PurePath(pathComponents.subList(fromIndex, toIndex).joinToString("/"))

    fun asList(): List<String> = this.pathComponents

    operator fun plus(pp: PurePath): PurePath = this.resolve(pp)

    operator fun div(pp: PurePath): PurePath = this.resolve(pp)

    override fun toString(): String = this.pathStr

    override fun equals(other: Any?): Boolean {
        return (other is PurePath) && (other.isAbsolute == this.isAbsolute) && (other.pathStr == this.pathStr)
    }

    override fun hashCode(): Int {
        return pathStr.hashCode()
    }
}

