package com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel


import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asmaa.khb.foodmenuapp.data.entities.Category
import com.asmaa.khb.foodmenuapp.data.entities.Product
import com.asmaa.khb.foodmenuapp.data.repositories.TableRepository
import com.asmaa.khb.foodmenuapp.data.resource.Resource
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.states.CategoriesUiState
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.states.OrderViewState
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.states.ProductsUiState
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.states.SearchFieldState
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.states.TableIntent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TableViewModel(private val tableRepository: TableRepository) : ViewModel() {

    private val _categoriesUiState = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Loading)
    val categoriesUiState: StateFlow<CategoriesUiState> = _categoriesUiState.asStateFlow()

    private val _productsUiStateFlow =
        MutableStateFlow(ProductsUiState(true, emptyList(), emptyList(), null))
    val productsUiStateFlow: StateFlow<ProductsUiState> = _productsUiStateFlow

    private val _orderViewState = MutableStateFlow<OrderViewState>(OrderViewState.Empty)
    val orderViewState: StateFlow<OrderViewState> = _orderViewState

    private val _searchState = MutableStateFlow<SearchFieldState>(SearchFieldState.Idle)

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    /* variables to handel pagination */
    private var currentPage: Int = 0
    private val limit: Int = 10
    private var endLoadMore: Boolean = false
    private var selectedCategoryId = 0

    /* variables to handel selected-products */
    private val selectedProducts = mutableMapOf<String, Product>()
    var selectedTabIndex = mutableIntStateOf(selectedCategoryId)

    init {
        initSearchDebounce()
        handleIntent(TableIntent.FetchCategories)
    }

    fun handleIntent(intent: TableIntent) {
        when (intent) {
            is TableIntent.FetchCategories -> fetchCategories()
            is TableIntent.FetchProducts -> {
                updateSelectedCategoryId(intent.categoryId)
                fetchProducts()
            }
        }
    }

    /*
     * #####################################################
     * ########## FETCH CATEGORIES/PRODUCTS APIs  ##########
     * ####################################################
     */
    private fun fetchCategories() {
        viewModelScope.launch {
            tableRepository.getCategoriesList().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _categoriesUiState.value = CategoriesUiState.Loading
                    }

                    is Resource.Success -> {
                        _categoriesUiState.value = CategoriesUiState.Success(resource.data)
                    }

                    is Resource.Error -> {
                        _categoriesUiState.value =
                            CategoriesUiState.Error(resource.message, resource.data ?: emptyList())
                    }
                }
            }
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            tableRepository.getProductList(selectedCategoryId, ++currentPage, limit)
                .collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            updateProductUiState(result = emptyList(), loading = true)
                        }

                        is Resource.Success -> {
                            if (resource.data.isEmpty()) {
                                endLoadMore = true
                            }
                            updateProductUiState(result = resource.data)
                        }

                        is Resource.Error -> {
                            if (resource.data.isNullOrEmpty()) {
                                endLoadMore = true
                            }
                            updateProductUiState(
                                result = resource.data ?: emptyList(), error = resource.message
                            )
                        }
                    }
                }
        }
    }


    private fun updateProductUiState(
        result: List<Product>, loading: Boolean = false, error: String? = null
    ) {
        _productsUiStateFlow.update {
            val results = (_productsUiStateFlow.value.list + result).distinctBy { it.id }
            ProductsUiState(
                loading = loading,
                list = results,
                selectedItems = selectedProducts.values.toList(),
                error = error
            )
        }
    }


    /*
   * #####################################################
   * ########## LOAD NEXT PAGE AND PAGINATION ##########
   * ####################################################
   */
    fun onCategoryTabClick(category: Category, index: Int) {
        selectedTabIndex.intValue = index
        restProductPagination()
        updateSelectedCategoryId(category.id)
        loadNextPage()
    }

    private fun restProductPagination() {
        currentPage = 0
        endLoadMore = false
        restProductUiStateFlow()
    }

    private fun restProductUiStateFlow() {/* rest products state flow*/
        _productsUiStateFlow.update {
            ProductsUiState(
                loading = true,
                list = emptyList(),
                selectedItems = selectedProducts.values.toList(),
                error = ""
            )
        }
    }

    fun loadNextPage() {
        if (!endLoadMore) {
            fetchProducts()
        }
    }

    private fun updateSelectedCategoryId(id: Int) {
        selectedCategoryId = id
    }

    /*
    * ###################################################
    * ############# HANDEL SEARCH FUNCTIONALITY ##########
    * ####################################################
    */
    @OptIn(FlowPreview::class)
    private fun initSearchDebounce() {
        viewModelScope.launch {
            combine(_query, _searchState) { query, state -> query to state }.debounce(1000)
                .distinctUntilChanged().collectLatest { (query, state) ->
                    when (state) {
                        is SearchFieldState.EmptyActive -> {
                            fetchSearchResults("")
                            _searchState.value = SearchFieldState.Idle
                        }

                        is SearchFieldState.Idle -> return@collectLatest
                        is SearchFieldState.WithInputActive -> fetchSearchResults(query)
                    }
                }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        if (newQuery.isNotBlank()) {
            _searchState.value = SearchFieldState.WithInputActive(_query.value)
        }
    }

    fun clearSearchField() {
        _query.value = ""
        _searchState.value = SearchFieldState.EmptyActive
    }

    fun updateSearchActiveState(isActive: Boolean) {
        _searchState.value = when {
            !isActive -> SearchFieldState.Idle
            else -> SearchFieldState.WithInputActive(_query.value)
        }
    }

    private suspend fun fetchSearchResults(query: String) {
        tableRepository.onSearchProductItem(selectedCategoryId, query).collect { res ->
            _productsUiStateFlow.update {
                ProductsUiState(
                    list = res, selectedItems = selectedProducts.values.toList()
                )
            }
        }
    }


    /*
      * ###################################################
      * #### HANDEL SELECTED PRODUCTs LOGIC WITH ORDER-VIEW ####
      * ####################################################
      */
    fun selectProductItem(product: Product) {
        viewModelScope.launch {
            product.selectedQuantity++
            selectedProducts[product.id] = product
            updateUiState()
            updateOrderView()
        }
    }

    private suspend fun updateOrderView() {
        delay(100) /* smaller delay for smother change on the order view */
        val totalPrice = selectedProducts.values.map { it.price * it.selectedQuantity }.sum()
        val selectedCount = selectedProducts.values.sumOf { it.selectedQuantity }
        _orderViewState.value =
            OrderViewState.Data(itemCount = selectedCount, totalPrice = totalPrice)
    }

    fun resetOrderViewState() {
        viewModelScope.launch {
            _orderViewState.value = OrderViewState.Rest
            selectedProducts.values.map { it.selectedQuantity = 0 }
            selectedProducts.clear()
            updateUiState()
        }
    }

    private fun updateUiState() {
        _productsUiStateFlow.value =
            _productsUiStateFlow.value.copy(selectedItems = selectedProducts.values.map { it.copy() })
    }
}
