package com.ibenew.wanandroid.mvvm.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ibenew.wanandroid.R

/**
 * Create by wuyt on 2019/12/9 17:41
 * {@link }
 */
class WechatFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wechat, container, false)
    }
}