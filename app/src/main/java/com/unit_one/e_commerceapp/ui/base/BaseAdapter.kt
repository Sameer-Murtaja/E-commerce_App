package com.unit_one.e_commerceapp.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.unit_one.e_commerceapp.BR

abstract class BaseAdapter<T>(
    @LayoutRes private val layoutId: Int,
    internal var items: List<T>?,
    private val listener: IBaseInteractionListener?
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val currentItem = items?.get(position)
        when (holder) {
            is ItemViewHolder -> {
                holder.binding.setVariable(BR.item, currentItem)
                holder.binding.setVariable(BR.listener, listener)
            }
        }
    }

    fun setItems(newItems: List<T>?) {
        items = newItems ?: emptyList()
        notifyDataSetChanged()
    }

    fun getItems() = items

    override fun getItemCount() = items?.size ?: 0

    class ItemViewHolder(binding: ViewDataBinding) : BaseViewHolder(binding)

    abstract class BaseViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)
}