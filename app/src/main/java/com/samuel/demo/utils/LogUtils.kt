package com.samuel.demo.utils;

import android.util.Log
import com.samuel.demo.BuildConfig

/**
 * Created by android on 2018/4/8.
 */

object LogUtils {

    private const val TAG = "samuel_log"
    private const val isDebug = BuildConfig.BUILD_TYPE == "debug"

    fun d(msg: String? = null, tag: String = TAG) {
        if (!msg.isNullOrEmpty() && isDebug) {
            Log.d(tag, "$msg, \n thread: ${Thread.currentThread().id}")
        }
    }

    fun e(msg: String? = null, tag: String = TAG) {
        if (!msg.isNullOrEmpty() && isDebug) {
            Log.e(tag, "$msg, \n thread: ${Thread.currentThread().id}")
        }
    }
}
