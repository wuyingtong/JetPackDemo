package com.ibenew.wanandroid.http

import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Create by wuyt on 2019/12/16 9:49
 * {@link }
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    /**
     * 返回LiveData<T>类型
     */
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            throw IllegalArgumentException("返回值必须是LiveData类型")
        }
        //获取第一个泛型类型
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != BaseResponse::class.java) {
            throw IllegalArgumentException("返回值必须是BaseResponse类型")
        }
        if (observableType !is ParameterizedType) {//<T>该类型
            throw IllegalArgumentException("类型错误,eg:BaseResponse<T>")
        }

        return LiveDataCallAdapter<Any>(observableType)
    }


    /**
     * 请求适配器
     */
    class LiveDataCallAdapter<T>(private val responseType: Type) : CallAdapter<T, LiveData<T>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): LiveData<T> {
            return object : LiveData<T>() {
                //这个作用是业务在多线程中,业务处理的线程安全问题,确保单一线程作业
                val flag = AtomicBoolean(false)

                override fun onActive() {
                    super.onActive()
                    if (flag.compareAndSet(false, true)) {
                        call.enqueue(object : Callback<T> {
                            override fun onResponse(call: Call<T>?, response: Response<T>) {
                                LogUtils.d("onResponse")
                                postValue(response.body())
                            }

                            override fun onFailure(call: Call<T>?, t: Throwable) {
                                LogUtils.d("onFailure：${t.message}")
                                postValue(null)
                            }
                        })
                    }
                }
            }
        }
    }
}