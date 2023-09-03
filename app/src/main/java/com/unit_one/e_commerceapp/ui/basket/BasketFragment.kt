package com.unit_one.e_commerceapp.ui.basket

import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.databinding.FragmentBasketBinding
import com.unit_one.e_commerceapp.ui.base.BaseFragment
import com.unit_one.e_commerceapp.ui.home.adapters.ProductAdapter

class BasketFragment : BaseFragment<FragmentBasketBinding, BasketViewModel>(
    R.layout.fragment_basket,
    BasketViewModel::class.java
) {
    override fun setup() {
        binding.rvProducts.adapter = ProductAdapter(emptyList(), viewModel)
    }
}