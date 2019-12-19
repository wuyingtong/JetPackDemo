package com.ibenew.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ibenew.sunflower.adapter.MY_GARDEN_PAGE_INDEX
import com.ibenew.sunflower.adapter.PLANT_LIST_PAGE_INDEX
import com.ibenew.sunflower.adapter.SunflowerPageAdapter
import com.ibenew.sunflower.databinding.FragmentViewPagerBinding
import com.ibenew.sunflower.view.TabLayoutMediator

/**
 * Create by wuyt on 2019/12/17 15:33
 * {@link }
 */
class HomeViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = SunflowerPageAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabText(position)
        }.attach()


        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabText(position: Int): String {
        return when(position){
            MY_GARDEN_PAGE_INDEX -> getString(R.string.my_garden_title)
            PLANT_LIST_PAGE_INDEX -> getString(R.string.plan_list_title)
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabIcon(position: Int): Int {
        return when(position){
            MY_GARDEN_PAGE_INDEX -> R.drawable.garden_tab_selector
            PLANT_LIST_PAGE_INDEX -> R.drawable.plan_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }
}