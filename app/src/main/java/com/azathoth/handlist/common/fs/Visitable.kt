package com.azathoth.handlist.common.fs


interface Visitable<T, D> {
    fun accept(visitor: Visitor<T, D>)
}