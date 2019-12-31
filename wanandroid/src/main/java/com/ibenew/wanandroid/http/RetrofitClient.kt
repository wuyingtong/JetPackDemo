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

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .hostnameVerifier(SSLSocketClient.hostnameVerifier)
        .sslSocketFactory(SSLSocketClient.sslSocketFactory, SSLSocketClient.trustManager)
        .addInterceptor(logInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        //.addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun logInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                LogUtils.i(message)
            }
        })
        logger.level = HttpLoggingInterceptor.Level.BODY

        return logger
    }

    fun getOkHttpClient() = okHttpClient

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: RetrofitClient? = null

        const val BASE_URL = "https://www.wanandroid.com/"

        fun getInstance(): RetrofitClient {
            return instance ?: synchronized(this) {
                instance ?: RetrofitClient().also { instance = it }
            }
        }
    }
}