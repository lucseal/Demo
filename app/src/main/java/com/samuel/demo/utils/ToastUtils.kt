package com.samuel.demo.utils

import android.app.Application
import android.view.Gravity
import android.widget.Toast
import java.lang.ref.SoftReference

/**
 * Created by android on 2018/4/8.
 */

object ToastUtils {
    private var sToast: Toast? = null
    private var weakContext: SoftReference<Application>? = null

    fun init(application: Application) {
        weakContext = SoftReference(application)
    }

    fun show(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (msg.isNullOrEmpty()) {
            return
        }

        val app = weakContext?.get() ?: return

        if (sToast == null) {
            sToast = Toast.makeText(app, msg, duration)
        } else {
            sToast?.duration = duration
            sToast?.setText(msg)
        }

        sToast?.setGravity(Gravity.CENTER, 0, 0)

        sToast?.show()
    }
}
