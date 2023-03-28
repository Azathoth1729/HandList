package com.azathoth.handlist.fs


interface Visitable<T, D> {
    fun accept(visitor: Visitor<T, D>)
}