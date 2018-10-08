package com.samuel.demo.rx


import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @Description:
 * @author sunyao
 * @date 2018/8/20 下午3:53
 */

object RxUtil {
    private val schedulersTransformer: FlowableTransformer<Any, Any> = FlowableTransformer {
        it
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> apply(): FlowableTransformer<T, T> {
        return schedulersTransformer as FlowableTransformer<T, T>
    }


}