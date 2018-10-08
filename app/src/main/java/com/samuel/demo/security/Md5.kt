package com.samuel.demo.security;

import java.lang.StringBuilder
import java.security.MessageDigest

/**
 * Created by android on 2018/4/4.
 */

object Md5 {

    public fun encode(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        md.update(str.toByteArray())
        val byteArray = md.digest()
        val result = StringBuilder()
        var cell: String
        for (value in byteArray) {
            cell = Integer.toHexString(0xFF and value.toInt())
            while (cell.length < 2) {
                cell = "0$cell"
            }
            result.append(cell)
        }
        return result.toString()
    }

}
