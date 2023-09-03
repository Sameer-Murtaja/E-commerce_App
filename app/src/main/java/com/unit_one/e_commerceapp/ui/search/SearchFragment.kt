package com.unit_one.e_commerceapp.ui.search

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.databinding.FragmentSearchBinding
import com.unit_one.e_commerceapp.ui.MainActivity
import com.unit_one.e_commerceapp.ui.base.BaseFragment
import com.unit_one.e_commerceapp.ui.base.BaseViewModel


class SearchFragment : BaseFragment<FragmentSearchBinding, BaseViewModel>(
    R.layout.fragment_search,
    BaseViewModel::class.java
) {
    private val args : SearchFragmentArgs by navArgs()


    override fun setup() {
        binding.apply {
            actionBarWithBackAndSearch.textInputEditSearch.setText(args.searchValue)
            binding.actionBarWithBackAndSearch.onClickBackButton = ::onClickBackButton
        }
    }


    private fun onClickBackButton() {
        findNavController().navigateUp()
        (requireActivity() as MainActivity).shouldBottomNavigationDisappear(false)
    }


}