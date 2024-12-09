package com.asmaa.khb.foodmenuapp.data.databases


import androidx.room.Database
import androidx.room.RoomDatabase
import com.asmaa.khb.foodmenuapp.data.entities.Category
import com.asmaa.khb.foodmenuapp.data.entities.Product

@Database(entities = [Category::class, Product::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun productsDao(): ProductsDao
}