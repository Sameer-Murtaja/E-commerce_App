package com.unit_one.e_commerceapp.ui.basket

import android.view.View
import androidx.lifecycle.*
import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.repository.ProductRepository
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import com.unit_one.e_commerceapp.ui.home.IProductListener
import kotlinx.coroutines.launch

class BasketViewModel : BaseViewModel(), IProductListener {
    private val productRepository = ProductRepository()

    val basketProducts = MutableLiveData<State<List<ProductResponse>?>>()


    init {
        getProductsInBasket()
    }

    private fun getProductsInBasket() {
        viewModelScope.launch {
//            Won't work with fake api
        }
    }

    override fun updateProductIsFavorite(product: ProductResponse) {
        product.isFavorite = product.isFavorite != true
        updateProduct(product)
    }


    private fun updateProduct(product: ProductResponse) {
        viewModelScope.launch {
          productRepository.updateProduct(product.id, product)
        }
    }


    override fun onClickItem(v: View, id: Int, image: String, title: String?) {

    }

}