package com.ibenew.wanandroid.http

import com.blankj.utilcode.util.LogUtils
import com.google.gson.GsonBuilder
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
        .addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                LogUtils.i(message)
            }
        }).apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .serializeNulls()
                    .enableComplexMapKeySerialization().create()
            )
        )
        .build()

    fun getOkHttpClient() = okHttpClient

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: RetrofitClient? = null

        //const val BASE_URL = "https://www.wanandroid.com/"
        //const val BASE_URL = "http://sms.bravat.com:9000/"
        const val BASE_URL = "http://192.168.30.61:7101/"

        fun getInstance(): RetrofitClient {
            return instance ?: synchronized(this) {
                instance ?: RetrofitClient().also { instance = it }
            }
        }
    }
}