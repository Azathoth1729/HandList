package com.azathoth.handlist.common.fs

typealias FileVisitable<T> = Visitable<T, File<T>>
typealias FileVisitor<T> = Visitor<T, File<T>>