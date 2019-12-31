package com.ibenew.wanandroid.mvvm.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.ibenew.wanandroid.R
import com.ibenew.wanandroid.adapter.HomeAdapter
import com.ibenew.wanandroid.databinding.FragmentHomeBinding
import com.ibenew.wanandroid.extension.GlideImageLoader
import com.ibenew.wanandroid.mvvm.viewmodel.HomeViewModel
import com.ibenew.wanandroid.utils.InjectorUtils
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

/**
 * Create by wuyt on 2019/12/18 9:39
 * {@link }
 */
class HomeFragment : Fragment() {
    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        //BarUtils.setStatusBarColor(binding.homeHeader.statusBar, Color.argb(0, 0, 0, 0))

        binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        ).apply {
            viewModel.loadData(true)

            val adapter = HomeAdapter()
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            homeHeader.banner
                .setImageLoader(GlideImageLoader())//图片加载器
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)//圆点指示器
                .setBannerAnimation(Transformer.CubeIn)//动画
                .setDelayTime(2000)//轮播图片间隔时间(默认2L)
                .isAutoPlay(true)
                .setOnBannerListener {

                }
            subscribeUi(adapter)
        }

        return binding.root
    }

    private fun subscribeUi(adapter: HomeAdapter) {
        viewModel.apply {
            //banner
            banners.observe(viewLifecycleOwner, Observer {
                LogUtils.d("banner size :${it.size}")
                binding.homeHeader.banner.setImages(it).start()//图片集合
            })

            //article
            articles.observe(viewLifecycleOwner, Observer {
                //adapter.submitList(it)
            })

            articleDataSource.observe(viewLifecycleOwner, Observer {
                LogUtils.d("paging：${it.size}")
                adapter.submitList(it)
            })
        }
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