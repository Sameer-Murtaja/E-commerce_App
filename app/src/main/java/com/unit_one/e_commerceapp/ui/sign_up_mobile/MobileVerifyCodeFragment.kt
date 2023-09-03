package com.unit_one.e_commerceapp.ui.sign_up_mobile

import android.content.Intent
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.model.User
import com.unit_one.e_commerceapp.data.repository.UserRepository
import com.unit_one.e_commerceapp.databinding.FragmentMobileVerifyCodeBinding
import com.unit_one.e_commerceapp.ui.MainActivity
import com.unit_one.e_commerceapp.ui.base.BaseFragment
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import com.unit_one.e_commerceapp.ui.groups.GroupsFullListFragmentArgs
import com.unit_one.e_commerceapp.util.Constants

class MobileVerifyCodeFragment : BaseFragment<FragmentMobileVerifyCodeBinding, BaseViewModel>(
    R.layout.fragment_mobile_verify_code,
    BaseViewModel::class.java
) {
    private lateinit var auth: FirebaseAuth
    private val args: MobileVerifyCodeFragmentArgs by navArgs()


    override fun setup() {
        auth = Firebase.auth
    }

    override fun addCallbacks() {
        binding.buttonVerify.setOnClickListener {
            verifyCode(binding.pinViewCode.text.toString())
        }
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(args.storedVerificationId, code)
        signInByCredentials(credential)
    }

    private fun signInByCredentials(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    log("signInWithCredential:success")

                    val user = task.result?.user
                    val newUser = User(user?.uid, user?.displayName, user?.email, null, user?.phoneNumber, null, null)
                    UserRepository().addUser(newUser)
                    startActivity(Intent(requireContext(), MainActivity::class.java))

                } else {
                    log("signInWithCredential:failure ${task.exception}")

                }
            }
    }

}