package com.asmaa.khb.foodmenuapp.data.databases


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asmaa.khb.foodmenuapp.data.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Query("SELECT * FROM product WHERE categoryId = :catId ORDER BY insertedAt ASC LIMIT :limit OFFSET :offset")
    fun getProductsByCategoryId(catId: Int, limit: Int, offset: Int): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE categoryId = :catId AND lower(name) LIKE '%' || lower(:query) || '%'")
    fun searchProductsByCategoryId(catId: Int, query: String): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(items: List<Product>)
}