package com.samuel.demo.data


/**
 * Created by android on 2018/3/16.
 */

object DataRepository {
    private var mRemoteService = RetrofitFactory.Builder()
            .baseUrl("http://39.106.135.187:8080")
            .dynamicParameter(object : RetrofitFactory.OnDynamicParameterListener {
                //add sim header
            })
            .debug(true)
            .build()
            .create(RemoteService::class.java)


    fun getRemote(): RemoteService {
        return mRemoteService
    }
}
