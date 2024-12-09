package com.asmaa.khb.foodmenuapp.data.repositories

import com.asmaa.khb.foodmenuapp.data.apis.ApiService
import com.asmaa.khb.foodmenuapp.data.databases.CategoriesDao
import com.asmaa.khb.foodmenuapp.data.databases.ProductsDao
import com.asmaa.khb.foodmenuapp.data.entities.Category
import com.asmaa.khb.foodmenuapp.data.entities.Product
import com.asmaa.khb.foodmenuapp.data.models.CategoriesResponse
import com.asmaa.khb.foodmenuapp.data.models.ProductsResponse
import com.asmaa.khb.foodmenuapp.data.resource.Resource
import com.asmaa.khb.foodmenuapp.data.resource.networkBoundResource
import kotlinx.coroutines.flow.Flow

interface TableRepository {
    suspend fun getCategoriesList(): Flow<Resource<List<Category>>>
    suspend fun getProductList(
        categoryId: Int, offset: Int, limit: Int
    ): Flow<Resource<List<Product>>>

    suspend fun onSearchProductItem(selectedCategoryId: Int, query: String): Flow<List<Product>>
}

class TableRepositoryImp(
    private val apiService: ApiService,
    private val categoriesDao: CategoriesDao,
    private val productsDao: ProductsDao,
) : TableRepository {

    /*
     * ###################################################
     * ###################### APIs ######################
     * ##################################################
     */
    override suspend fun getCategoriesList(): Flow<Resource<List<Category>>> =
        networkBoundResource(
            query = {
                categoriesDao.getAllCategories()
            }, fetch = {
                apiService.fetchCategories()
            }, map = {
                mapCategoriesResult(it)
            }, saveFetchResult = { categories ->
                categoriesDao.run {
                    categoriesDao.saveCategories(categories)
                }
            })


    override suspend fun getProductList(
        categoryId: Int, offset: Int, limit: Int
    ): Flow<Resource<List<Product>>> =
        networkBoundResource(
            query = {
                val pageSize = (offset - 1) * limit
                productsDao.getProductsByCategoryId(categoryId, limit, pageSize)
            }, fetch = {
                apiService.fetchProducts(categoryId, offset, limit)
            }, map = {
                mapProductsResult(it)
            }, saveFetchResult = { categories ->
                productsDao.run {
                    productsDao.saveProducts(categories)
                }
            })

    /*
    * ######################################################
    * ###################### MAPPING ######################
    * #####################################################
   */
    private fun mapCategoriesResult(responseCategoriesList: List<CategoriesResponse>): List<Category> {
        val list = mutableListOf<Category>()
        for (category in responseCategoriesList) {
            list.add(Category(id = category.id, name = category.name))
        }
        return list
    }

    private fun mapProductsResult(responseProductsList: List<ProductsResponse>): List<Product> {
        val list = mutableListOf<Product>()
        for (product in responseProductsList) {
            list.add(
                Product(
                    id = product.id,
                    categoryId = product.category.id,
                    name = product.name,
                    description = product.description,
                    image = product.image,
                    price = product.price
                )
            )
        }
        return list
    }

    /*
    * ######################################################
    * ###################### SEARCH ######################
    * #####################################################
    */
    override suspend fun onSearchProductItem(selectedCategoryId: Int, query: String) =
        productsDao.searchProductsByCategoryId(selectedCategoryId, query)
}


