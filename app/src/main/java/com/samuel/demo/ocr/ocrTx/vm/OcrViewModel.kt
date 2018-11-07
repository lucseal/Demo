package com.samuel.demo.ocr.ocrTx.vm

import android.app.Application
import com.samuel.demo.base.BaseVM
import com.samuel.demo.ocr.bean.TxOcrResult
import com.samuel.demo.data.DataRepository
import com.samuel.demo.ex.getData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

/**
 * @Description:
 * @author sunyao
 * @date 2018/9/19 下午3:11
 */


class OcrViewModel(application: Application) : BaseVM(application) {

    fun requestOcr(url: String, headers: HashMap<String, String>, appId: String, imageBase64: String): Single<TxOcrResult> {
        val jsonObj = JSONObject()
        jsonObj.put("appid", appId)
        jsonObj.put("image", imageBase64)
        val requestBody = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), jsonObj.toString())
        return DataRepository.getRemote().requestOcr(url, headers, requestBody).getData().observeOn(AndroidSchedulers.mainThread())
    }

}