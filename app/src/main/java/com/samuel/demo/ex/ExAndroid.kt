package com.samuel.demo.ex

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


/** 格式化
 * 99.99 to "99.99"
 * 99.00 to "99"
 */
fun Float.asInt(): String {
    val fakeInt = this.toInt()
    val fakeFloat = fakeInt.toFloat()
    return if (this == fakeFloat) fakeInt.toString() else this.toString()
}

/** 英文首字母从小写转换为大写*/
fun String.captureName(): String {
    if (this.isEmpty()) {
        return ""
    }
    val cs = this.toCharArray()
    cs[0] = cs[0] - 32
    return String(cs)
}

fun View.location(): Rect {
    val location = IntArray(2)
    getLocationOnScreen(location)
    val rect = Rect()
    rect.left = location[0]
    rect.top = location[1]
    rect.right = rect.left + width
    rect.bottom = rect.top + height
    return rect
}

fun TextView.textResColor(colorId: Int) {
    setTextColor(resources.getColor(colorId))
}

fun View.backgroundResColor(colorId: Int) {
    setBackgroundColor(resources.getColor(colorId))
}

fun View.getString(strRes: Int): String {
    return context.getString(strRes)
}

//fun View.getString(strRes: Int, vararg formatArgs: Any): String {
//    return context.getString(strRes, formatArgs)
//}

fun Resources.statusBarHeight(): Int {
    var statusBarHeight = -1
    val resourceId = getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = getDimensionPixelSize(resourceId)
    }
    return statusBarHeight
}


fun String.dateFormat(format: String, local: Locale? = null): Date {
    val sdf: SimpleDateFormat = if (local != null) SimpleDateFormat(format, local) else SimpleDateFormat(format)
    return sdf.parse(this)
}


fun String.dateGmtFormat(): Date {
    val gmtDate = this.dateFormat("dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH)
    val time = gmtDate.time
    val tz = TimeZone.getDefault()
    val targetTime = time + (tz.dstSavings + tz.rawOffset)
    return Date(targetTime)
}

fun Date.format(format: String, local: Locale? = null): String {
    val sdf: SimpleDateFormat = if (local != null) SimpleDateFormat(format, local) else SimpleDateFormat(format)
    return sdf.format(this)
}


fun Int.timeFormat(): String {
    val totalSeconds = (this + 500) / 1000//四舍五入
    val seconds = totalSeconds % 60
    val minutes = totalSeconds / 60 % 60
    val hours = totalSeconds / 60 / 60
    var timeStr = ""
    timeStr += when {
        hours > 9 -> "$hours:"
        hours > 0 -> "0$hours:"
        else -> ""
    }
    timeStr += when {
        minutes > 9 -> "$minutes:"
        minutes > 0 -> "0$minutes:"
        else -> "00:"
    }
    timeStr += when {
        seconds > 9 -> seconds
        seconds > 0 -> "0$seconds"
        else -> "00"
    }
    return timeStr
}

fun File.unzip(): Boolean {
    if (!name.endsWith("zip")) {
        return false
    }
    var zip: ZipInputStream? = null
    var outputStream: FileOutputStream? = null
    try {
        zip = ZipInputStream(FileInputStream(this))
        var szName: String
        var entry: ZipEntry? = zip.nextEntry
        while (entry != null) {
            szName = entry.name
            val file = File(parent + File.separator + szName)
            outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            while (true) {
                val length = zip.read(buffer)
                if (length <= 0) {
                    break
                }
                outputStream.write(buffer, 0, length)
                outputStream.flush()
            }
            outputStream.close()
            entry = zip.nextEntry
        }
        return true
    } catch (e: Exception) {
        return false
    } finally {
        zip?.close()
        outputStream?.close()
    }

}

fun ViewGroup.forEach(action: (View) -> Unit) {
    for (i in 0 until this.childCount) {
        action(getChildAt(i))
    }
}

fun ViewGroup.childs(action: (View) -> Unit) {
    for (i in 0 until this.childCount) {
        action(getChildAt(i))
    }
}


/**
 * 隐藏软键盘
 */
fun EditText.hideSoftInput() {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun EditText.editDisable() {
    inputType = InputType.TYPE_NULL
    setTextIsSelectable(false)
    isCursorVisible = false
//    isFocusable = enable
//    isFocusableInTouchMode = enable
}

fun EditText.hideSoftInputForNoFocus() {
    this.setOnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) {
            this.clearFocus()
            hideSoftInput()
        }
    }
}

/**
 * 请求焦点
 */
fun View.focusOn() {
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    this.requestFocus()
}


fun View.touchIn(ev: MotionEvent): Boolean {

    val vRect = location()
    if (ev.rawX < vRect.left || ev.rawX > vRect.right
            || ev.y < vRect.top || ev.rawY > vRect.bottom) {
        return false
    }
    return true
}


fun View.snapshot(): Bitmap? {
    this.isDrawingCacheEnabled = true
    val bitmap = getDrawingCache(false)

    var result: Bitmap? = null
    if (bitmap != null) {
        result = Bitmap.createBitmap(bitmap)
    }
    this.isDrawingCacheEnabled = false
    return result
}

fun Int.resIdToColor(context: Context): Int {
    return ContextCompat.getColor(context, this)
}

fun Int.resId2Drawable(context: Context): Drawable? {
    return ContextCompat.getDrawable(context, this)
}

fun ImageView.recycle(remove: Boolean = true) {
    val src = this.drawable
    if (src != null && src is BitmapDrawable) {
        val bitmap = src.bitmap
        bitmap?.recycle()
        setImageDrawable(null)
    }
    val bg = this.background
    if (bg != null && bg is BitmapDrawable) {
        val bitmap = bg.bitmap
        bitmap?.recycle()
        this.background = null
    }

    if (remove) {
        val vp = this.parent
        if (vp != null && vp is ViewGroup) {
            vp.removeView(this)
        }
    }
}


fun ValueAnimator.addFrameUpdateListener(action: (animation: ValueAnimator) -> Unit, frame: Int) {
    val limit = (60 / frame).toInt()
    var count = 0
    this.addUpdateListener {
        if (count < limit - 1) {
            count += 1
        } else {
            count = 0
            action(it)
        }
    }
}