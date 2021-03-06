package com.samuel.demo.ocr.ocrXf.vm

import android.app.Application
import com.samuel.demo.base.BaseVM
import com.samuel.demo.data.DataRepository
import com.samuel.demo.ex.getData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @Description:
 * @author sunyao
 * @date 2018/9/19 下午3:11
 */


class XfOcrViewModel(application: Application) : BaseVM(application) {

    fun requestOcr(url: String, headers: HashMap<String, String>, image: String): Single<Any> {
        return DataRepository.getRemote().requestXfOcr(url, headers, image).getData().observeOn(AndroidSchedulers.mainThread())
    }

}