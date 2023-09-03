package com.unit_one.e_commerceapp.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragmentWithoutData<VB:ViewBinding>: Fragment() {
    abstract val LOG_TAG:String
    abstract val bindingInflater: (LayoutInflater) -> VB
    private lateinit var _binding: VB
    protected val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        addCallbacks()

    }

    abstract fun setup()

    abstract fun addCallbacks()

    protected fun log(value:Any){
        Log.v(LOG_TAG,value.toString())
    }
}