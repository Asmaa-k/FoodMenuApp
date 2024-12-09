package com.asmaa.khb.foodmenuapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "categoryId")
    var categoryId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "price")
    val price: Float,

    @ColumnInfo(name = "quantity")
    var selectedQuantity: Int = 0,

    @ColumnInfo(name = "insertedAt")
    val insertedAt: Long = System.currentTimeMillis()
)
