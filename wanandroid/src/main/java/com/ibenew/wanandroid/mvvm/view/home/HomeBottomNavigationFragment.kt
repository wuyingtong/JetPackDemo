package com.ibenew.wanandroid.mvvm.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.ibenew.wanandroid.R
import com.ibenew.wanandroid.databinding.FragmentHomeBottomNavigationBinding
import com.ibenew.wanandroid.extension.setupWithNavController

/**
 * Create by wuyt on 2019/12/18 9:57
 * {@link }
 */
class HomeBottomNavigationFragment : Fragment() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentHomeBottomNavigationBinding>(
            inflater,
            R.layout.fragment_home_bottom_navigation,
            container,
            false
        )
            .apply {
                val navGraphIds = listOf(
                    R.navigation.nav_home,
                    R.navigation.nav_square,
                    R.navigation.nav_wechat,
                    R.navigation.nav_category,
                    R.navigation.nav_project
                )

                val controller = bottomNavigationView.setupWithNavController(
                    navGraphIds = navGraphIds,
                    fragmentManager = childFragmentManager,
                    containerId = R.id.nav_home_container,
                    intent = requireActivity().intent
                )

                controller.observe(viewLifecycleOwner, Observer {
                    // View.GONE
                    setupWithNavController(toolbar, it)
                })

                currentNavController = controller

                //使用自定义图片切换,不用默认颜色
                //bottomNavigationView.itemIconTintList = null
            }
            .root
    }
}