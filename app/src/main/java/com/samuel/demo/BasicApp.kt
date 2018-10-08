package com.samuel.demo

import android.app.Application
import com.samuel.demo.utils.ToastUtils

/**
 * @Description:
 * @author sunyao
 * @date 2018/9/19 下午2:06
 */

class BasicApp : Application() {

    companion object {
        lateinit var instance: BasicApp
    }


    override fun onCreate() {
        super.onCreate()

        instance = this

        ToastUtils.init(this)

    }
}