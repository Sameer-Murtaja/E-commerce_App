package com.unit_one.e_commerceapp.ui.home

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation.findNavController
import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.repository.ProductRepository
import com.unit_one.e_commerceapp.ui.groups.GroupType
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(), IHomeListener {

    private val productRepository = ProductRepository()

    val products = MutableLiveData<State<List<ProductResponse>?>>()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            productRepository.getProducts().collect {
                products.postValue(it)
            }
        }
    }

    fun getTitles() = productRepository.getTitles()


    override fun updateProductIsFavorite(product: ProductResponse) {
        product.isFavorite = product.isFavorite != true
        updateProduct(product)
    }

    private fun updateProduct(product: ProductResponse) {
        viewModelScope.launch {
            productRepository.updateProduct(product.id, product)
        }
    }

    override fun onClickGroupIcon(v: View, groupType: GroupType) {
        val action = HomeFragmentDirections.actionHomeFragmentToGroupsFullListFragment(groupType)
        findNavController(v).navigate(action)
    }

    override fun onClickItem(v: View, id: Int, image: String, title: String?) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id, image, title)
        findNavController(v).navigate(action)
    }

}