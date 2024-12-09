package com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.states

import com.asmaa.khb.foodmenuapp.data.entities.Category
import com.asmaa.khb.foodmenuapp.data.entities.Product

sealed class TableIntent {
    data object FetchCategories : TableIntent()
    data class FetchProducts(val categoryId: Int) : TableIntent()
}

sealed class CategoriesUiState {
    data object Loading : CategoriesUiState()
    data class Success(val categories: List<Category>) : CategoriesUiState()
    data class Error(val message: String, val cachedCategories: List<Category>) :
        CategoriesUiState()
}

sealed class SearchFieldState {
    data object Idle : SearchFieldState()
    data object EmptyActive : SearchFieldState()
    data class WithInputActive(val query: String) : SearchFieldState()
}

sealed class OrderViewState {
    data object Empty : OrderViewState()
    data class Data(val itemCount: Int = 0, val totalPrice: Float = 0f) : OrderViewState()
    data object Rest : OrderViewState()
}

data class ProductsUiState(
    val loading: Boolean = false,
    val list: List<Product> = emptyList(),
    val selectedItems: List<Product>,
    val error: String? = null
)