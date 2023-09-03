package com.unit_one.e_commerceapp.ui.sign_in

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.model.User
import com.unit_one.e_commerceapp.data.repository.UserRepository
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel : BaseViewModel() {
    private val userRepository = UserRepository()
    private val auth = Firebase.auth


    private val _signInState = MutableLiveData<State<Boolean>>()
    val signInState get() = _signInState
    val textEmail = MutableLiveData<String?>()
    val textPassword = MutableLiveData<String?>()


    fun onClickButtonSignIn() {
        _signInState.postValue(State.Loading)
        if (checkInputs()) {
            viewModelScope.launch {
                userRepository.logInUser(textEmail.value.toString(), textPassword.value.toString())
                    .collect {
                        _signInState.postValue(it)
                    }
            }
        }
    }

    private fun checkInputs() = isInputsFilled()

    private fun isInputsFilled(): Boolean {
        val isInputsFilled = !textEmail.value.isNullOrBlank() &&
                !textPassword.value.isNullOrBlank()

        if (!isInputsFilled) {
            _signInState.postValue(State.Error("please fill the fields"))
        }
        return isInputsFilled
    }

    fun navigateToMobileSignUp(v: View) {
        val action = SignInFragmentDirections.actionSignInFragmentToMobileSignUpFragment()
        Navigation.findNavController(v).navigate(action)
    }

    fun navigateToSignUp(v: View) {
        val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        Navigation.findNavController(v).navigate(action)
    }


    fun signInGoogleUser(idToken: String) {
        _signInState.postValue(State.Loading)
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                log("sign in success? ${it.isSuccessful}")
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    val newUser = User(
                        user?.uid,
                        user?.displayName,
                        user?.email,
                        null,
                        user?.phoneNumber,
                        null,
                        null
                    )
                    viewModelScope.launch {
                        userRepository.addUserToFirebase(newUser).collect { state ->
                            _signInState.postValue(state)
                        }
                    }
                }
            }
    }

}