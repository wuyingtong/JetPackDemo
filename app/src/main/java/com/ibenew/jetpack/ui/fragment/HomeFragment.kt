package com.ibenew.jetpack.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.ibenew.jetpack.R
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * Create by wuyt on 2019/12/9 10:05
 * {@link }
 */
class HomeFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        collapsing_toolbar_layout.setupWithNavController(
            toolbar,
            navController,
            appBarConfiguration
        )

        app_bar.addOnOffsetChangedListener(this)

//        recycler_view.layoutManager = LinearLayoutManager(context)
//        recycler_view.setHasFixedSize(true)
//        recycler_view.itemAnimator = DefaultItemAnimator()
//        recycler_view.adapter = HomeAdapter(context,listData)

        btn_login.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {

        if (appBarLayout.totalScrollRange + verticalOffset == 0) {
            // 折叠
            collapsing_toolbar_layout.title = resources.getString(R.string.home)

        } else {
            // 展开
            collapsing_toolbar_layout.title = ""
        }
    }
}
