package com.samuel.demo.ex

import com.samuel.demo.base.BaseThrowable
import com.samuel.demo.data.BaseResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

fun <R> Single<BaseResponse<R>>.getData(): Single<R> {
    return compose { upstream ->
        upstream.subscribeOn(Schedulers.io()).flatMap {
            if (it.isSuccess()) {
                return@flatMap Single.just(it.data)
            } else {
                return@flatMap Single.error<R>(BaseThrowable(it.message, it.code))
            }
        }
    }
}