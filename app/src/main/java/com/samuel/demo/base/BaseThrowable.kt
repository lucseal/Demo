package com.samuel.demo.base

/**
 * Created by android on 2018/4/4.
 */
class BaseThrowable : Throwable {

    var code: String = ""

    constructor() : super()

    constructor(message: String) : super(message)

    constructor(message: String, code: String) : super(message) {
        this.code = code
    }

}