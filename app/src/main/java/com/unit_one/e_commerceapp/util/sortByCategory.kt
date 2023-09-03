package com.unit_one.e_commerceapp.util

import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.data.remote.StoreAPI

fun getClothes(products: List<ProductResponse>?): List<ProductResponse> {
    return products?.filter {
        it.category == "men's clothing" ||
                it.category == "women's clothing"
    } ?: emptyList()
}

fun getElectronics(products: List<ProductResponse>?): List<ProductResponse> {
    val titles = StoreAPI.titlesList
    return products?.filter {
        it.category == titles[1].lowercase()
    } ?: emptyList()
}
fun getJewelery(products: List<ProductResponse>?): List<ProductResponse> {
    val titles = StoreAPI.titlesList
    return products?.filter {
        it.category == titles[2].lowercase()
    } ?: emptyList()
}