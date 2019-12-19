package com.ibenew.wanandroid.view.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.BarUtils
import com.ibenew.wanandroid.databinding.FragmentHomeBinding
import com.ibenew.wanandroid.view.extension.GlideImageLoader
import com.ibenew.wanandroid.viewmodel.HomeViewModel
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import timber.log.Timber

/**
 * Create by wuyt on 2019/12/18 9:39
 * {@link }
 */
class HomeFragment : Fragment() {
    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

    private lateinit var viewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewModel = ViewModelProvider.NewInstanceFactory().create(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        BarUtils.setStatusBarColor(binding.homeHeader.statusBar, Color.argb(0, 0, 0, 0))

        subscribeBanner()

        return binding.root
    }

    private fun subscribeBanner() {
        viewModel.banners.observe(this, Observer {
            Timber.d("banner size :${it.size}")

            binding.homeHeader.banner
                .setImageLoader(GlideImageLoader())//图片加载器
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)//圆点指示器
                .setImages(it)//图片集合
                .setBannerAnimation(Transformer.CubeIn)//动画
                .setDelayTime(2000)//轮播图片间隔时间(默认2L)
                .isAutoPlay(true)
                .setOnBannerListener {

                }
                .start()

        })
    }

    override fun onStart() {
        super.onStart()
        binding.homeHeader.banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        binding.homeHeader.banner.stopAutoPlay()
    }
}