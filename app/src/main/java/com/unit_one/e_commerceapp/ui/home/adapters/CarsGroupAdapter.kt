package com.unit_one.e_commerceapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.databinding.ItemCarsGroupBinding
import com.unit_one.e_commerceapp.databinding.ItemCarsGroupGridBinding
import com.unit_one.e_commerceapp.ui.base.BaseAdapter
import com.unit_one.e_commerceapp.ui.base.IBaseInteractionListener

class CarsGroupAdapter(
    list: List<ProductResponse>,
    listener: IBaseInteractionListener?,
    private val isGroupCard: Boolean
) :
    BaseAdapter<ProductResponse>(R.layout.item_cars_group, list, listener) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return if (isGroupCard) {
            val binding =
                ItemCarsGroupGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemViewHolder(binding)
        } else {
            val binding =
                ItemCarsGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemViewHolder(binding)
        }

    }
}