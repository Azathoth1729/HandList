package com.azathoth.handlist.fs

typealias FileVisitable<T> = Visitable<T, File<T>>
typealias FileVisitor<T> = Visitor<T, File<T>>