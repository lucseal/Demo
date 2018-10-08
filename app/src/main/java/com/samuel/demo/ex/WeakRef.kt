package com.samuel.demo.ex

import java.lang.ref.WeakReference

class WeakRef<T> constructor(t: T) {

    private val weakRef = WeakReference(t)

    operator fun invoke(): T? {
        return weakRef.get()
    }
}

fun <T : Any> T.weakRef() = WeakRef<T>(this)