package com.unit_one.e_commerceapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StoreAPI {
    private val BASE_URL = "https://fakestoreapi.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val productApiService: ProductApiService = retrofit.create(ProductApiService::class.java)

    val titlesList: List<String> = listOf(
        "Clothes",
        "Electronics",
        "Jewelery",
        "New Products"
    )

}