package com.unit_one.e_commerceapp.ui.details

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.databinding.FragmentProdutcDetailsBinding
import com.unit_one.e_commerceapp.ui.MainActivity
import com.unit_one.e_commerceapp.ui.base.BaseFragment
import com.unit_one.e_commerceapp.ui.chat.ChatFragment


class ProductDetailsFragment : BaseFragment<FragmentProdutcDetailsBinding, ProductDetailsViewModel>(
    R.layout.fragment_produtc_details,
    ProductDetailsViewModel::class.java
) {
   private val args: ProductDetailsFragmentArgs by navArgs()

    override fun setup() {
        (requireActivity() as MainActivity).shouldBottomNavigationDisappear(true)

        binding.actionBarWithBack.onClickBackButton = ::onClickBackButton

        binding.apply {
            args.apply {
                viewModel?.getProduct(id)
            }
            val action =
                ProductDetailsFragmentDirections.actionDetailsFragmentToChatFragment(args.id.toString())
            childFragmentManager.beginTransaction().replace(R.id.commentsFragment, ChatFragment::class.java, action.arguments)
                .commit()
        }
    }


    private fun onClickBackButton() {
        findNavController().navigateUp()
        (requireActivity() as MainActivity).shouldBottomNavigationDisappear(false)
    }


}