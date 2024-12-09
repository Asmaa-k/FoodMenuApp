package com.asmaa.khb.foodmenuapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductsResultResponse(
    @SerialName("result") val result: List<ProductsResponse>,
)