package com.unit_one.e_commerceapp.ui.home

import android.view.View
import com.unit_one.e_commerceapp.ui.base.IBaseInteractionListener
import com.unit_one.e_commerceapp.ui.groups.GroupType

interface IHomeListener : IBaseInteractionListener, IProductListener {
    fun onClickGroupIcon(v: View, groupType: GroupType)

}