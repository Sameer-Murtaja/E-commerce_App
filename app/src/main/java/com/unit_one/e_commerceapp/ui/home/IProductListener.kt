package com.unit_one.e_commerceapp.ui.home

import android.view.View
import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.ui.base.IBaseInteractionListener

interface IProductListener : IBaseInteractionListener {
    fun onClickItem(v: View, id: Int, image: String, title: String? = null)
    fun updateProductIsFavorite(product: ProductResponse)

}