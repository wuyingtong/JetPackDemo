package com.ibenew.jetpack

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        // 绑定toolbar
        // 通过目标点的label属性，自动更新title,并自动添加返回箭头（startDestination除外）
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        // 绑定BottomNavigationView,自动切换对应fragment
        bottom_navigation_view.setupWithNavController(navController)

        // 首页隐藏toolbar,其他bottom item隐藏返回箭头
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.squareFragment,
                R.id.wechatFragment, R.id.categoryFragment,
                R.id.projectFragment -> {
                    if (destination.id == R.id.homeFragment) {
                        toolbar.visibility = View.GONE
                    } else {
                        toolbar.visibility = View.VISIBLE
                    }

                    toolbar.navigationIcon = null
                    bottom_navigation_view.visibility = View.VISIBLE
                }
                else -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.navigationIcon =
                        resources.getDrawable(R.drawable.ic_arrow_white_black_24dp, null)

                    bottom_navigation_view.visibility = View.GONE
                }
            }
        }
    }
}
