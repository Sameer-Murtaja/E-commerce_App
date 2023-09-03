package com.unit_one.e_commerceapp.ui.sign_in

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.databinding.FragmentSignInBinding
import com.unit_one.e_commerceapp.ui.CustomDialogFragment
import com.unit_one.e_commerceapp.ui.MainActivity
import com.unit_one.e_commerceapp.ui.base.BaseFragment


class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>(
    R.layout.fragment_sign_in,
    SignInViewModel::class.java
) {
    lateinit var googleSignInClient: GoogleSignInClient
    val GOOGLE_SIGN_IN = 400


    override fun addCallbacks() {
        viewModel.signInState.observe(this) { signInState ->
            if (signInState is State.Success) {
                startMainActivity()
            } else if (signInState is State.Error) {
                showErrorInputsDialog(signInState.message)
            }
        }

        binding.buttonSignInGoogle.setOnClickListener {
            signInGoogle()
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

    private fun signInGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val i = googleSignInClient.signInIntent
        startActivityForResult(i, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        log("onActivity REsult")
        if (requestCode == GOOGLE_SIGN_IN) {
        log("requestCode = GOOGLE_SIGN_IN")
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                account.idToken?.let { viewModel.signInGoogleUser(it) }
            } catch (e: ApiException) {
                log("catch Error: ${e.message}")
            }
        }
    }

}