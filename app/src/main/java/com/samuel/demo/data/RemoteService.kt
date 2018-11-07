package com.samuel.demo.data

import com.samuel.demo.ocr.bean.TxOcrResult
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @Description:
 * @author sunyao
 * @date 2018/9/19 下午2:17
 */
interface RemoteService {
    @POST
    fun requestOcr(@Url url: String,
                   @HeaderMap headers: Map<String, String>,
                   @Body requestBody: RequestBody)
            : Single<BaseResponse<TxOcrResult>>

    @POST
    @FormUrlEncoded
    fun requestXfOcr(@Url url: String,
                     @HeaderMap headers: Map<String, String>,
                     @Field("image") image: String)
            : Single<BaseResponse<Any>>

}