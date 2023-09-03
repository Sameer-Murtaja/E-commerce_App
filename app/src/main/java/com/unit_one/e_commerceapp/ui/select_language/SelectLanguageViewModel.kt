package com.unit_one.e_commerceapp.ui.select_language

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectLanguageViewModel: ViewModel() {

    private val _selectEnglish = MutableLiveData<Boolean>()
    val selectEnglish get() = _selectEnglish

    private val _onDoneClicked = MutableLiveData<Boolean>()
    val onDoneClicked get() = _onDoneClicked


    fun selectEnglish(){
        _selectEnglish.postValue(true)
    }

    fun selectArabic(){
        _selectEnglish.postValue(false)
    }

    fun onClickDone(){
        _onDoneClicked.postValue(true)
    }

}