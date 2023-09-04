package com.unit_one.e_commerceapp.ui.groups

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.databinding.FragmentGroupsFullListBinding
import com.unit_one.e_commerceapp.ui.MainActivity
import com.unit_one.e_commerceapp.ui.base.BaseFragment
import com.unit_one.e_commerceapp.ui.home.adapters.ElectronicAdapter
import com.unit_one.e_commerceapp.ui.home.adapters.ClothAdapter
import com.unit_one.e_commerceapp.util.getClothes
import com.unit_one.e_commerceapp.util.getElectronics
import com.unit_one.e_commerceapp.util.getJewelery

class GroupsFullListFragment :
    BaseFragment<FragmentGroupsFullListBinding, GroupsFullListViewModel>(
        R.layout.fragment_groups_full_list,
        GroupsFullListViewModel::class.java
    ) {
    private val args: GroupsFullListFragmentArgs by navArgs()

    override fun setup() {
        (requireActivity() as MainActivity).shouldBottomNavigationDisappear(true)

        binding.actionBarWithBackAndSearch.onClickBackButton = ::onClickBackButton

        viewModel.products.observe(this) { products ->
            viewModel.title.postValue(args.groupType.name)

            when (args.groupType) {
                GroupType.CLOTHES -> {
                    val clothesList = getClothes(products.toData())
                    binding.recyclerViewMainGroup.adapter =
                        ClothAdapter(clothesList, viewModel, true)
                }
                GroupType.ELECTRONIC -> {
                    val electronicsList = getElectronics(products.toData())
                    binding.recyclerViewMainGroup.layoutManager =
                        GridLayoutManager(requireContext(), 2)
                    binding.recyclerViewMainGroup.adapter =
                        ElectronicAdapter(electronicsList, viewModel, true)
                }
                GroupType.JEWELERY -> {
                    val jeweleryList = getJewelery(products.toData())
                    binding.recyclerViewMainGroup.layoutManager =
                        GridLayoutManager(requireContext(), 2)
                    binding.recyclerViewMainGroup.adapter =
                        ElectronicAdapter(jeweleryList, viewModel, true)
                }
            }
        }

    }

    private fun onClickBackButton() {
        findNavController().navigateUp()
        (requireActivity() as MainActivity).shouldBottomNavigationDisappear(false)
    }


}

