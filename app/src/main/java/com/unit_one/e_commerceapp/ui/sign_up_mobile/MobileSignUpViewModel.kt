package com.unit_one.e_commerceapp.ui.sign_up_mobile

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.model.User
import com.unit_one.e_commerceapp.data.repository.UserRepository
import com.unit_one.e_commerceapp.ui.MainActivity
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import java.util.concurrent.TimeUnit

class MobileSignUpViewModel : BaseViewModel() {
    private val auth = Firebase.auth

    lateinit var activity: Activity

    private val _signUpState = MutableLiveData<State<String?>>()
    val signUpState get() = _signUpState

    val mobileNumber = MutableLiveData<String?>()
    val verificationNumber = MutableLiveData<String?>()


    fun onClickSignUp() {
        signUpState.postValue(State.Loading)

        val phoneNumber = mobileNumber.value ?: ""

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
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
            signInByCredentials(credential)
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

            signUpState.postValue(State.Success(verificationId))
        }
    }


    private fun signInByCredentials(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                log("fast signInWithCredential:success")
                val name = "User${System.currentTimeMillis().toString().takeLast(5)}"
                val user = it.user
                val newUser = User(
                    user?.uid,
                    user?.displayName ?: name,
                    user?.email,
                    null,
                    user?.phoneNumber,
                    null,
                    null
                )
                UserRepository().addUserToFirebase(newUser)
                _signUpState.postValue(State.Success(null))
            }
            .addOnFailureListener {
                log("fast signInWithCredential:failure ${it.message}")
            }
    }



    fun verifyCode() {
        _signUpState.postValue(State.Loading)
        val code = verificationNumber.value.toString()
        val credential = PhoneAuthProvider.getCredential(signUpState.value?.toData().toString(), code)
        signInByCredentials(credential)
    }



}