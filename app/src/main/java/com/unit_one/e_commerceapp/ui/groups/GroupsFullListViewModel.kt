package com.unit_one.e_commerceapp.ui.groups

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.repository.ProductRepository
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class GroupsFullListViewModel : BaseViewModel(), IGroupListener {

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

    override fun onClickItem(v: View, id: Int, image: String, title: String?) {
        val action = GroupsFullListFragmentDirections.actionGroupsFullListFragmentToDetailsFragment(
            id,
            image,
            title
        )
        Navigation.findNavController(v).navigate(action)
    }

    fun onClickBackButton(){
        Log.e("TAG", "onClickBackButton: GroupsFullListViewModel", )
    }

}