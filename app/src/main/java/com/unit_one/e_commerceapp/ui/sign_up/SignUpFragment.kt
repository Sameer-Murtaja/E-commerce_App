package com.unit_one.e_commerceapp.ui.sign_up

import android.content.Intent
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.databinding.FragmentSignUpBinding
import com.unit_one.e_commerceapp.ui.MainActivity
import com.unit_one.e_commerceapp.ui.base.BaseFragment
import com.unit_one.e_commerceapp.ui.CustomDialogFragment

class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>(
    R.layout.fragment_sign_up,
    SignUpViewModel::class.java
) {

    override fun addCallbacks() {
        viewModel.signUpState.observe(this) { signUpState ->
            if (signUpState is State.Success) {
                startMainActivity()
            } else if (signUpState is State.Error) {
                showErrorInputsDialog(signUpState.message)
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }

    private fun showErrorInputsDialog(message: String) {
        val customDialogFragment =
            CustomDialogFragment("Wrong inputs", message)
        val ft = requireActivity().supportFragmentManager.beginTransaction()
        customDialogFragment.show(ft, "custom_dialog_fragment")
    }

}