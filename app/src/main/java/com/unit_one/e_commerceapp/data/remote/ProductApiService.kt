package com.unit_one.e_commerceapp.data.remote

import com.unit_one.e_commerceapp.data.model.CartResponse
import com.unit_one.e_commerceapp.data.model.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(@Query("limit") amount: Int? = null): Response<List<ProductResponse>>

    @GET("products/{product_id}")
    suspend fun getProduct(@Path("product_id") productId: Int): Response<ProductResponse>

    @GET("products/{product_id}")
    suspend fun updateProduct(@Path("product_id") productId: Int, @Body body: ProductResponse): Response<ProductResponse>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>


    @GET("carts")
    suspend fun getCarts(): Response<List<CartResponse>>

    @GET("carts/user/{user_id}")
    suspend fun getUserCarts(@Path("user_id") userId: Int): Response<List<CartResponse>>

}