package com.unit_one.e_commerceapp.ui.home.adapters

import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.ui.base.BaseAdapter
import com.unit_one.e_commerceapp.ui.base.IBaseInteractionListener

class ProductAdapter(list: List<ProductResponse>, listener: IBaseInteractionListener?) :
    BaseAdapter<ProductResponse>(R.layout.item_product,list,listener)