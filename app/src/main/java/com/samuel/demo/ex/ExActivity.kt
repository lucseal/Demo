package com.samuel.demo.ex

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.view.*
import android.widget.EditText
import kotlin.concurrent.thread

private val handler = Handler(Looper.getMainLooper())

fun Activity.hideSoftInputExceptEdit(ev: MotionEvent) {
    val view = currentFocus
    if (view != null && view is EditText && !view.touchIn(ev)) {
        view.hideSoftInput()
    }
}

fun Activity.hideSoftInputExceptEdit() {
    val view = rootView()
    view?.setOnClickListener {
        val focusView = currentFocus
        if (focusView != null && focusView is EditText) {
            focusView.hideSoftInput()
        }
    }
}


fun Activity.rootView(): View? {
    val contentView = window.decorView.findViewById<ViewGroup>(android.R.id.content)
    if (contentView != null && contentView.childCount > 0) {
        return contentView.getChildAt(0)
    }
    return null
}


fun Activity.fullScreen() {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun Activity.taskRunOnUiThread(action: () -> Unit) {
    runOnUiThread {
        action()
    }
}

fun Fragment.taskRunOnUiThread(action: () -> Unit) {
    activity?.runOnUiThread {
        action()
    }
}

fun Activity.taskAsync(action: () -> Unit) {
    thread {
        action()
    }
}

fun Activity.taskRunOnUiDelay(action: () -> Unit, delay: Long) {
    handler.postDelayed(action, delay)
}