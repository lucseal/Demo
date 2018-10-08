package com.samuel.demo.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by android on 2018/4/11.
 */
class VMCallbackFactory(val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (BaseVM::class.java.isAssignableFrom(modelClass)) {
            return modelClass.getConstructor(Application::class.java).newInstance(application)
        }else if(AndroidViewModel::class.java.isAssignableFrom(modelClass)){
            return modelClass.getConstructor(Application::class.java).newInstance(application)
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}