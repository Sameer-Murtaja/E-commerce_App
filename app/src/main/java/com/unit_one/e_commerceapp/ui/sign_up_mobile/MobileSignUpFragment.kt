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
import com.unit_one.e_commerceapp.databinding.FragmentMobileSignUpBinding
import com.unit_one.e_commerceapp.ui.base.BaseFragment
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import com.unit_one.e_commerceapp.ui.home.HomeFragmentDirections
import com.unit_one.e_commerceapp.util.Constants
import java.util.concurrent.TimeUnit

class MobileSignUpFragment : BaseFragment<FragmentMobileSignUpBinding, BaseViewModel>(
    R.layout.fragment_mobile_sign_up,
    BaseViewModel::class.java
) {
   private lateinit var auth: FirebaseAuth
   private lateinit var phoneNumber: String


    override fun setup() {
        auth = Firebase.auth
    }

    override fun addCallbacks() {
        binding.buttonMobileSignUp.setOnClickListener {
            phoneNumber = binding.editTextPhone.text.toString()

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(requireActivity()) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            log("onVerificationCompleted:$credential")
//            val code = credential.smsCode
//            if (code != null) {
//                verifyCode(code)
//            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            log("onVerificationFailed ${e.message}")

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            log("onCodeSent:$verificationId")

            navigateToVerifyCode(verificationId)
        }
    }

    fun navigateToVerifyCode(verificationId: String) {
        val action = MobileSignUpFragmentDirections.actionMobileSignUpFragmentToMobileVerifyCodeFragment(verificationId, phoneNumber)
        Navigation.findNavController(binding.root).navigate(action)
    }


}