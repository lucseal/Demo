package com.samuel.demo.ex

import java.util.regex.Pattern

 fun String.isMobile(): Boolean {
    if (this.isNotEmpty() && this.length == 11 && this.startsWith("1")) {
        return true
    }
    return false
}

 fun String.verifyPassword():Boolean{
    val rex = "^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{6,20}\$"
    return Pattern.matches(rex, this)
}