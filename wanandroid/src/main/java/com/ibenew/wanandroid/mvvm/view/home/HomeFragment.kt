package com.ibenew.wanandroid.mvvm.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.appbar.AppBarLayout
import com.ibenew.wanandroid.R
import com.ibenew.wanandroid.adapter.HomeAdapter
import com.ibenew.wanandroid.databinding.FragmentHomeBinding
import com.ibenew.wanandroid.extension.GlideImageLoader
import com.ibenew.wanandroid.http.NetworkState
import com.ibenew.wanandroid.http.Status
import com.ibenew.wanandroid.mvvm.viewmodel.HomeViewModel
import com.ibenew.wanandroid.utils.InjectorUtils
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer


/**
 * Create by wuyt on 2019/12/18 9:39
 * {@link }
 */
class HomeFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {
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

        val adapter = HomeAdapter()

        binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        ).apply {

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
        }

        viewModel.refresh()

        subscribeUi(adapter)


        return binding.root
    }

    private fun subscribeUi(adapter: HomeAdapter) {
        viewModel.apply {
            //banner
            banners.observe(viewLifecycleOwner, Observer {
                val imagePaths = mutableListOf<String>()
                it?.data?.let { banners ->
                    banners.forEach { banner -> imagePaths.add(banner.imagePath) }
                }
                try {
                    binding.homeHeader.banner.setImages(imagePaths).start()//图片集合
                } catch (e: ArithmeticException) {

                }
            })

            articles.observe(viewLifecycleOwner, Observer {
                it?.data?.datas?.let {
                    adapter.submitList(it)
                }
            })

            repoResult.observe(viewLifecycleOwner, Observer {
                LogUtils.d("pagedList：${it.size}")
                adapter.submitList(it)
            })

            networkState.observe(viewLifecycleOwner, Observer {
                LogUtils.d("netWorkState：${it.status}--${it.msg}")
            })

            refreshState.observe(viewLifecycleOwner, Observer {
                LogUtils.d("refreshState：${it.status}--${it.msg}")
                binding.swipeRefresh.isRefreshing = it == NetworkState.LOADING
                if (it.status == Status.FAILED) {
                    ToastUtils.showShort(it.msg)
                }
            })

            binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                binding.fab.isVisible = layoutManager.findFirstVisibleItemPosition() > 5
            }
        })

        binding.fab.setOnClickListener { binding.recyclerView.smoothScrollToPosition(0) }
    }

    override fun onStart() {
        super.onStart()
        binding.homeHeader.banner.startAutoPlay()
        binding.appBar.addOnOffsetChangedListener(this)
    }

    override fun onStop() {
        super.onStop()
        binding.homeHeader.banner.stopAutoPlay()
        binding.appBar.removeOnOffsetChangedListener(this)
    }

    /*
     * 监听AppBarLayout的伸缩事件，当AppBar展开时开启下拉刷新,收缩时禁用下拉刷新
     */
    override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
        binding.swipeRefresh.isEnabled = p1 == 0
    }
}