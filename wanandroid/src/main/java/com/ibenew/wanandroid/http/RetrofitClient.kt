package com.ibenew.wanandroid.http

import com.blankj.utilcode.util.LogUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Create by wuyt on 2019/12/18 15:01
 * []
 */
class RetrofitClient private constructor() {

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .client(getOkHttpClient())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .hostnameVerifier(SSLSocketClient.hostnameVerifier)
            .sslSocketFactory(SSLSocketClient.sslSocketFactory, SSLSocketClient.trustManager)
            .addInterceptor(logInterceptor())
            .build()

    }

    private fun logInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                LogUtils.i(message)
            }
        })
        logger.level = HttpLoggingInterceptor.Level.BODY

        return logger
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: RetrofitClient? = null

        private lateinit var retrofit: Retrofit

        fun getInstance(): RetrofitClient {
            return instance ?: synchronized(this) {
                instance ?: RetrofitClient().also { instance = it }
            }
        }
    }
}