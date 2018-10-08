package com.samuel.demo.data


import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by android on 2018/3/22.
 */

class RetrofitFactory private constructor(builder: Builder) {
    private var mRetrofit: Retrofit
    private var mBuilder: Builder = builder

    init {
        val httpBuilder = OkHttpClient.Builder()
        httpBuilder.addInterceptor(RetrofitInterceptor())
        if (builder.isDebug) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpBuilder.addInterceptor(interceptor)
        }
        httpBuilder.connectTimeout(30L, TimeUnit.SECONDS)
        httpBuilder.readTimeout(30L, TimeUnit.SECONDS)
        httpBuilder.writeTimeout(30L, TimeUnit.SECONDS)
        val okHttpClient = httpBuilder.build()
        mRetrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(builder.baseUrl).build()
    }

    fun <T> create(cls: Class<T>): T {
        return mRetrofit.create(cls)
    }

    interface OnDynamicParameterListener {
        fun getHeader(): HashMap<String, String>? = null
    }

    class Builder {
        var headers: HashMap<String, String>? = null
            private set
        var baseUrl: String = ""
            private set
        var isDebug = false
            private set
        var onDynamicParameterListener: OnDynamicParameterListener? = null
            private set

        fun baseUrl(url: String): Builder {
            this.baseUrl = url
            return this
        }

        @Deprecated("use dynamicParameter()")
        fun headers(headers: HashMap<String, String>): Builder {
            this.headers = headers
            return this
        }

        fun debug(debug: Boolean): Builder {
            this.isDebug = debug
            return this
        }

        fun dynamicParameter(listener: OnDynamicParameterListener): Builder {
            this.onDynamicParameterListener = listener
            return this
        }

        fun build(): RetrofitFactory {
            return RetrofitFactory(this)
        }

    }


    private inner class RetrofitInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain?): Response {
            val originalRequest = chain!!.request()
            val builder = originalRequest.newBuilder()
            val listener = mBuilder.onDynamicParameterListener
            if (listener != null) {
                val headerMap = listener.getHeader()
                if (headerMap != null) {
                    val iterator = headerMap.iterator()
                    while (iterator.hasNext()) {
                        val entry = iterator.next()
                        builder.addHeader(entry.key, entry.value)
                    }
                }
            }

            val request = builder.build()
            return chain.proceed(request)
        }
    }
}
