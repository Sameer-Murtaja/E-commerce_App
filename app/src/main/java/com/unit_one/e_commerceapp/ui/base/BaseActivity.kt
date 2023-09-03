package com.unit_one.e_commerceapp.ui.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unit_one.e_commerceapp.BR

abstract class BaseActivity<DB : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutId: Int,
    private val viewModelClass: Class<VM>
) : AppCompatActivity() {
    val LOG_TAG: String = this::class.java.simpleName
    private lateinit var _binding: DB
    protected val binding get() = _binding
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = ViewModelProvider(this)[viewModelClass]
        _binding.lifecycleOwner = this
        _binding.setVariable(BR.viewModel, viewModel)
        setup()
        addCallbacks()
    }

    open fun setup(){}

    open fun addCallbacks(){}

    protected fun log(value:Any?){
        Log.e(LOG_TAG,value.toString())
    }
}