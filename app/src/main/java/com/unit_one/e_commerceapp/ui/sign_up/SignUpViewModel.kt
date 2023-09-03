package com.unit_one.e_commerceapp.ui.sign_up

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.model.User
import com.unit_one.e_commerceapp.data.repository.UserRepository
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SignUpViewModel : BaseViewModel() {
    private val userRepository = UserRepository()

    val textUsername = MutableLiveData<String?>()
    val textEmail = MutableLiveData<String?>()
    val textAddress = MutableLiveData<String?>()
    val textPhone = MutableLiveData<String?>()
    val textPassword = MutableLiveData<String?>()
    val textRepeatPassword = MutableLiveData<String?>()

    private val _signUpState = MutableLiveData<State<Boolean>>()
    val signUpState get() = _signUpState

    fun navigateToSignIn(v: View) {
        val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
        Navigation.findNavController(v).navigate(action)
    }

    fun onClickButtonSignUp() {
        _signUpState.postValue(State.Loading)
        if (checkInputs()) {
            val user = User(
                null,
                textUsername.value.toString(),
                textEmail.value.toString(),
                textAddress.value.toString(),
                textPhone.value.toString(),
                null,
                textPassword.value.toString()
            )
            viewModelScope.launch {
                userRepository.addUser(user).collect {
                    _signUpState.postValue(it)
                }
            }
        }
    }

    private fun checkInputs() = isInputsFilled() && isRepeatPasswordMatching()

    private fun isInputsFilled(): Boolean {
        val isInputsFilled = !textUsername.value.isNullOrBlank() &&
                !textEmail.value.isNullOrBlank() &&
                !textAddress.value.isNullOrBlank() &&
                !textPhone.value.isNullOrBlank() &&
                !textPassword.value.isNullOrBlank() &&
                !textRepeatPassword.value.isNullOrBlank()

        if (!isInputsFilled) {
            _signUpState.postValue(State.Error("please fill the fields"))
        }
        return isInputsFilled
    }

    private fun isRepeatPasswordMatching(): Boolean {
        val isRepeatPasswordMatching = textPassword.value == textRepeatPassword.value

        if (!isRepeatPasswordMatching) {
            _signUpState.postValue(State.Error("Password and repeat password does not match"))
        }

        return isRepeatPasswordMatching
    }


}