package com.unit_one.e_commerceapp.ui

import com.unit_one.e_commerceapp.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.unit_one.e_commerceapp.databinding.CustomDialogFragmentBinding


class CustomDialogFragment(val title:String, private val description:String) : DialogFragment() {

    lateinit var binding : CustomDialogFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CustomDialogFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            button.setOnClickListener {
                dismiss()
            }
            tvTitle.text = title
            tvDescription.text = description
        }
    }

    override fun getTheme() = R.style.RoundedCornersDialog

}