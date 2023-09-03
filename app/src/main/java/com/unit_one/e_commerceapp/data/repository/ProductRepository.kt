package com.unit_one.e_commerceapp.data.repository

import com.unit_one.e_commerceapp.data.model.CartResponse
import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.remote.StoreAPI
import com.unit_one.e_commerceapp.util.wrapWithFlow
import kotlinx.coroutines.flow.Flow

class ProductRepository {

    fun getTitles(): List<String>{
        return StoreAPI.titlesList
    }

    suspend fun getProducts(amount: Int? = null): Flow<State<List<ProductResponse>?>> {
        return wrapWithFlow(StoreAPI.productApiService::getProducts, amount)
    }


    suspend fun getProduct(productId: Int): Flow<State<ProductResponse?>>{
        return wrapWithFlow(StoreAPI.productApiService::getProduct, productId)
    }

    suspend fun updateProduct(productId: Int?, body: ProductResponse): Flow<State<ProductResponse?>>{
        return wrapWithFlow(StoreAPI.productApiService::updateProduct, productId, body)
    }

    suspend fun getUserCarts(userId: Int): Flow<State<List<CartResponse>?>>{
        return wrapWithFlow(StoreAPI.productApiService::getUserCarts, userId)
    }

//    suspend fun getCategories() = ProductAPI.apiService.getCategories()
}