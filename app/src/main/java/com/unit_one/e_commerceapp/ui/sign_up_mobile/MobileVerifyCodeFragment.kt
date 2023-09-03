package com.unit_one.e_commerceapp.ui.sign_up_mobile

import android.content.Intent
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.databinding.FragmentMobileVerifyCodeBinding
import com.unit_one.e_commerceapp.ui.MainActivity
import com.unit_one.e_commerceapp.ui.base.BaseFragment

class MobileVerifyCodeFragment :
    BaseFragment<FragmentMobileVerifyCodeBinding, MobileSignUpViewModel>(
        R.layout.fragment_mobile_verify_code,
        MobileSignUpViewModel::class.java
    ) {

    override fun setup() {
        viewModel.signUpState.observe(this) { signUpState ->
            if (signUpState is State.Success) {
                if (signUpState.data == null)
                    startMainActivity()
            }
        }
    }


    private fun startMainActivity() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }
}