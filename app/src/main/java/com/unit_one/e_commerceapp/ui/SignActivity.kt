package com.unit_one.e_commerceapp.ui

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.databinding.ActivitySignBinding
import com.unit_one.e_commerceapp.ui.base.BaseActivity
import com.unit_one.e_commerceapp.ui.sign_in.SignInViewModel


class SignActivity : BaseActivity<ActivitySignBinding, SignInViewModel>(
    R.layout.activity_sign,
    SignInViewModel::class.java
) {
    private lateinit var navController: NavController

    override fun addCallbacks() {
        binding.apply {
            navController = findNavController(R.id.nav_host_fragment_container2)
        }
    }

}