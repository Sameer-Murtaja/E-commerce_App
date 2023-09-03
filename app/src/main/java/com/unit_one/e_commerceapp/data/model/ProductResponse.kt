package com.unit_one.e_commerceapp.data.model


import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("is_favorite")
    var isFavorite: Boolean?,
)