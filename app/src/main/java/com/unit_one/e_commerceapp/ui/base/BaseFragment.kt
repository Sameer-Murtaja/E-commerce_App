package com.unit_one.e_commerceapp.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.unit_one.e_commerceapp.BR
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.ui.MainActivity

abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutId: Int,
    private val viewModelClass: Class<VM>
) : Fragment() {

    val LOG_TAG: String = this::class.java.simpleName

    lateinit var viewModel: VM

    private lateinit var _binding: DB
    protected val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            viewModel = ViewModelProvider(it)[viewModelClass]
        }
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        _binding.apply {
            setVariable(BR.viewModel, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }
        customSetup()
        setup()
        addCallbacks()
        return binding.root
    }

    private fun customSetup() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onClickBackButton()
        }

    }

    private fun onClickBackButton() {
        findNavController().navigateUp()
        val activity = requireActivity()
        if (activity is MainActivity)
            activity.shouldBottomNavigationDisappear(false)
    }

    open fun setup() {}

    open fun addCallbacks() {}

    protected fun log(value: Any?) {
        Log.e(LOG_TAG, value.toString())
    }


}