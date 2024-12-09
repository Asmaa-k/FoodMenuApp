package com.asmaa.khb.foodmenuapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("image") val image: String,
    @SerialName("price") val price: Float,
    @SerialName("category") val category: CategoriesResponse
)