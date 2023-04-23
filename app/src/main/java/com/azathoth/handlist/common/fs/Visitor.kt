package com.azathoth.handlist.common.fs

interface Visitor<T, D> {
    fun visitTree(tree: D): Visitor<T, D>

    fun visitData(cur: D)
}