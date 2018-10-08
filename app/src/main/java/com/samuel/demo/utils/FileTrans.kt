package com.samuel.demo.utils

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

/**
 * @Description:
 * @author sunyao
 * @date 2018/9/19 下午5:32
 */

object FileTrans {

    fun bitmap2base64(bitmap: Bitmap): String {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos)
        return Base64.encodeToString(bos.toByteArray(), Base64.NO_WRAP)
    }
}