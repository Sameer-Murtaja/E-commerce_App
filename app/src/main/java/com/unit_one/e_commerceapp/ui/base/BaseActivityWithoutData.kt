package com.unit_one.e_commerceapp.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivityWithoutData<VB:ViewBinding>: AppCompatActivity() {
    abstract val LOG_TAG:String
    abstract val bindingInflater: (LayoutInflater) -> VB
    private lateinit var _binding: VB
    protected val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(_binding.root)
        addCallbacks()
    }

    abstract fun addCallbacks()

    protected fun log(value:Any){
        Log.v(LOG_TAG,value.toString())
    }
}