package com.unit_one.e_commerceapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.databinding.ItemMainGroupBinding
import com.unit_one.e_commerceapp.databinding.ItemMainGroupGridBinding
import com.unit_one.e_commerceapp.ui.base.BaseAdapter
import com.unit_one.e_commerceapp.ui.base.IBaseInteractionListener

class ClothAdapter(
    list: List<ProductResponse>,
    listener: IBaseInteractionListener?,
    private val isGroupCard: Boolean
) :
    BaseAdapter<ProductResponse>(R.layout.item_main_group, list, listener) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return if (isGroupCard) {
            val binding =
                ItemMainGroupGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemViewHolder(binding)
        } else {
            val binding =
                ItemMainGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemViewHolder(binding)
        }

    }
}