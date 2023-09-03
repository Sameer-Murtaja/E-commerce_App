package com.unit_one.e_commerceapp.ui.sign_up_mobile

import android.content.Intent
import android.view.View
import androidx.navigation.Navigation
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.model.User
import com.unit_one.e_commerceapp.data.repository.UserRepository
import com.unit_one.e_commerceapp.databinding.FragmentMobileSignUpBinding
import com.unit_one.e_commerceapp.ui.MainActivity
import com.unit_one.e_commerceapp.ui.base.BaseFragment
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import com.unit_one.e_commerceapp.ui.home.HomeFragmentDirections
import com.unit_one.e_commerceapp.util.Constants
import java.util.concurrent.TimeUnit

class MobileSignUpFragment : BaseFragment<FragmentMobileSignUpBinding, MobileSignUpViewModel>(
    R.layout.fragment_mobile_sign_up,
    MobileSignUpViewModel::class.java
) {


    override fun setup() {
        viewModel.activity = requireActivity()

        viewModel.signUpState.observe(this) { signUpState ->
            if (signUpState is State.Success) {
                if (signUpState.data == null)
                    startMainActivity()
                else{
                    navigateToVerifyCode()
                }
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }

    private fun navigateToVerifyCode() {
        val action =
            MobileSignUpFragmentDirections.actionMobileSignUpFragmentToMobileVerifyCodeFragment()
        Navigation.findNavController(binding.root).navigate(action)
    }
}