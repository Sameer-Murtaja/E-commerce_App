package com.unit_one.e_commerceapp.ui

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.databinding.ActivityMainBinding
import com.unit_one.e_commerceapp.ui.base.BaseActivity
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import com.unit_one.e_commerceapp.util.Constants

class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>(
    R.layout.activity_main,
    BaseViewModel::class.java
) {

    private lateinit var navController: NavController

    override fun addCallbacks() {
        binding.apply {
            navController = findNavController(R.id.nav_host_fragment_container)
            bottomNavigationView.setupWithNavController(navController)

            if (intent.extras?.getString(Constants.nextActivity) == Constants.basket) {
                switchFragmentWithBottomNavigation(Page.BASKET)
            }
        }
    }

    enum class Page(val id: Int) {
        HOME(R.id.homeFragment),
        SEARCH(R.id.searchFragment),
        BASKET(R.id.basketFragment),
        ACCOUNT(R.id.accountFragment)
    }

    private fun switchFragmentWithBottomNavigation(page: Page) {
        binding.bottomNavigationView.selectedItemId = page.id
    }


    fun shouldBottomNavigationDisappear(shouldDisappear: Boolean) {
        if (shouldDisappear)
            binding.bottomNavigationView.visibility = View.GONE
        else
            binding.bottomNavigationView.visibility = View.VISIBLE
    }

}