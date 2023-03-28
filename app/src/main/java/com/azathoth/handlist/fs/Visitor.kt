package com.azathoth.handlist.fs

interface Visitor<T, D> {
    fun visitTree(tree: D): Visitor<T, D>

    fun visitData(cur: D)
}