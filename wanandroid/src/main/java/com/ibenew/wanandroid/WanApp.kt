package com.ibenew.wanandroid

import android.app.Application
import com.blankj.utilcode.util.Utils


/**
 * Create by wuyt on 2019/12/19 11:22
 * {@link }
 */
class WanApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Utils.init(this)
    }
}