package com.samuel.demo.base

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.samuel.demo.widget.LoadDialog

/**
 * @Description:
 * @author sunyao
 * @date 2018/9/19 下午3:00
 */
abstract class BaseActivity : AppCompatActivity() {
    private var dialog: LoadDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    protected fun <T : BaseVM> createViewModel(cls: Class<T>): T {
        return ViewModelProviders.of(this, VMCallbackFactory(application)).get(cls)
    }

    protected fun showLoadDialog() {
        if (dialog == null) {
            dialog = LoadDialog(this)
        }
        dialog?.show()
    }

    protected fun hideLoadDialog() {
        dialog?.hide()
    }

    override fun onDestroy() {
        dialog?.dismiss()
        dialog = null
        super.onDestroy()
    }

}