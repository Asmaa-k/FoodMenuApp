package com.asmaa.khb.foodmenuapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)