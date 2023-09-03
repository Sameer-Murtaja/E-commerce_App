package com.unit_one.e_commerceapp.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.repository.ProductRepository
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ProductDetailsViewModel : BaseViewModel() {
    private val productRepository = ProductRepository()

    val product = MutableLiveData<State<ProductResponse?>>()


    fun getProduct(id:Int){
        viewModelScope.launch {
            productRepository.getProduct(id).collect {
                product.postValue(it)
            }
        }
    }
}