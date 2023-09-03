package com.unit_one.e_commerceapp.data.model


import com.google.gson.annotations.SerializedName

data class CartResponse(
    @SerializedName("date")
    val date: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("products")
    val products: List<String?>?,
    @SerializedName("userId")
    val userId: Int?
)