package com.samuel.demo.widget

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import com.samuel.demo.R

/**
 * Created by android on 2018/4/4.
 */

public class LoadDialog(context: Context) {

    private var mContext: Context = context

    private var view: View
    private var dialog: AlertDialog? = null

    init {
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_load, null)
    }

    fun show() {
        if (dialog == null) {
            dialog = AlertDialog.Builder(mContext, R.style.dialog).setView(view).create();
        }

        dialog?.show()
    }

    fun hide() {
        dialog?.hide()
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}