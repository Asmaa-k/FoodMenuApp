package com.asmaa.khb.foodmenuapp.data.apis

import com.asmaa.khb.foodmenuapp.BuildConfig


object ApiConstants {
    private const val BASE_URL = "https://my.api.mockaroo.com"
    private const val API_KEY = BuildConfig.apiKey

    //######### MENU API FIELDs ###########//
    const val FIELD_CATEGORY_ID = "category_id"
    const val FIELD_OFFSET = "offset"
    const val FIELD_LIMIT = "limit"

    //######### MENU APIs ###########//
    const val GET_CATEGORIES = "$BASE_URL/categories.json?key=$API_KEY"
    const val GET_PRODUCTS = "$BASE_URL/products.json?&key=$API_KEY"
}
