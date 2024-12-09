package com.asmaa.khb.foodmenuapp.data.apis

import com.asmaa.khb.foodmenuapp.data.models.CategoriesResponse
import com.asmaa.khb.foodmenuapp.data.models.ProductsResponse
import com.asmaa.khb.foodmenuapp.data.models.ProductsResultResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface ApiService {
    suspend fun fetchCategories(): List<CategoriesResponse>
    suspend fun fetchProducts(categoryId: Int, offset: Int, limit: Int): List<ProductsResponse>
}


class ApiServiceImpl(private val client: HttpClient) : ApiService {
    override suspend fun fetchCategories() =
        client.get(ApiConstants.GET_CATEGORIES).body<List<CategoriesResponse>>()

    override suspend fun fetchProducts(categoryId: Int, offset: Int, limit: Int) =
        client.get(ApiConstants.GET_PRODUCTS) {
            url {
                parameters.append(ApiConstants.FIELD_CATEGORY_ID, categoryId.toString())
                parameters.append(ApiConstants.FIELD_OFFSET, offset.toString())
                parameters.append(ApiConstants.FIELD_LIMIT, limit.toString())
            }
        }.body<ProductsResultResponse>().result
}
