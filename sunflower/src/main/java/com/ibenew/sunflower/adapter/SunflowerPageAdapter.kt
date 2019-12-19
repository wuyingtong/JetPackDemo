package com.ibenew.sunflower.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ibenew.sunflower.GardenFragment
import com.ibenew.sunflower.PlantListFragment

/**
 * Create by wuyt on 2019/12/17 15:51
 * {@link }
 */

const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1

class SunflowerPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragmentCreators: Map<Int, () -> Fragment> = mapOf(
        MY_GARDEN_PAGE_INDEX to { GardenFragment() },
        PLANT_LIST_PAGE_INDEX to { PlantListFragment() }
    )

    override fun getItemCount() = tabFragmentCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}