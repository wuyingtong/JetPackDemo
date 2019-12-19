package com.ibenew.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.ibenew.sunflower.adapter.GardenPlantingAdapter
import com.ibenew.sunflower.adapter.PLANT_LIST_PAGE_INDEX
import com.ibenew.sunflower.data.AppDatabase
import com.ibenew.sunflower.databinding.FragmentGardenBinding
import com.ibenew.sunflower.utils.InjectorUtils
import com.ibenew.sunflower.viewmodel.GardenPlantingListViewModel

/**
 * Create by wuyt on 2019/12/17 15:28
 * {@link }
 */
class GardenFragment : Fragment() {

    private lateinit var binding: FragmentGardenBinding

    private val viewModel: GardenPlantingListViewModel by viewModels {
        InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGardenBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter

        binding.addPlant.setOnClickListener {
            //navigateToPlantListPage()
            AppDatabase.getInstance(requireContext()).gardenPlantingDao().getPlantedGardens()
        }

        subscribeUi(adapter, binding)

        return binding.root
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) {
            binding.hasPlantings = !it.isNullOrEmpty()
            adapter.submitList(it)
        }
    }

    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            PLANT_LIST_PAGE_INDEX
    }
}