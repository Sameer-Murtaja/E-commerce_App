package com.unit_one.e_commerceapp.ui.home

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.databinding.FragmentHomeBinding
import com.unit_one.e_commerceapp.ui.MainActivity
import com.unit_one.e_commerceapp.ui.base.BaseFragment
import com.unit_one.e_commerceapp.ui.home.adapters.HomeFragmentAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home,
    HomeViewModel::class.java
) {


    override fun setup() {
        binding.rvMain.adapter =
            HomeFragmentAdapter( viewModel, this)

    }

    override fun addCallbacks() {
        binding.apply {
            buttonQrScan.setOnClickListener {
                scanBarcode()
            }
        }
    }

    private fun scanBarcode() {
        val scanner = GmsBarcodeScanning.getClient(requireContext())
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                lifecycleScope.launch {
                    searchItem(barcode.rawValue)
                }
            }
            .addOnCanceledListener {
            }
            .addOnFailureListener {
            }
    }

    private suspend fun searchItem(value:String?){
        delay(100)
        val action =
            HomeFragmentDirections.actionHomeFragmentToSearchFragment(value)
        findNavController().navigate(action)
        (requireActivity() as MainActivity).shouldBottomNavigationDisappear(true)
    }


}