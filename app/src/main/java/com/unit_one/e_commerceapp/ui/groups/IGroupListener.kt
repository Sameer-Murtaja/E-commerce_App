package com.unit_one.e_commerceapp.ui.groups

import android.view.View
import com.unit_one.e_commerceapp.ui.base.IBaseInteractionListener

interface IGroupListener : IBaseInteractionListener{
    fun onClickItem(v: View, id: Int, image: String, title: String?)
}