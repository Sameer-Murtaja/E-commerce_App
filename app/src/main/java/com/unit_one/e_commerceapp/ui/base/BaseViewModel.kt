package com.unit_one.e_commerceapp.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel(){

    val LOG_TAG: String = this::class.java.simpleName
    val title = MutableLiveData(formatViewModelName(LOG_TAG))


    private fun formatViewModelName(input: String): String {
        val withoutViewModel = input.removeSuffix("ViewModel")
        val words = withoutViewModel.split("(?=[A-Z])".toRegex())
        return words.joinToString(" ")
    }

    protected fun log(value: Any?) {
        Log.e(LOG_TAG, value.toString())
    }
}