package com.asmaa.khb.foodmenuapp.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.asmaa.khb.foodmenuapp.R
import com.asmaa.khb.foodmenuapp.data.entities.Category
import com.asmaa.khb.foodmenuapp.data.entities.Product
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.mediumIconSize
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.smallIconSize
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.smallViewHeight
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.xlargePadding
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.xsmallPadding
import com.asmaa.khb.foodmenuapp.presentation.ui.utils.formatFloatNumber
import com.asmaa.khb.foodmenuapp.presentation.ui.utils.formatSingleDigitNumber
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.TableViewModel
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.states.CategoriesUiState
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.states.OrderViewState
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.states.TableIntent
import com.asmaa.khb.foodmenuapp.presentation.ui.views.CardView
import com.asmaa.khb.foodmenuapp.presentation.ui.views.EndlessLazyColumn
import com.asmaa.khb.foodmenuapp.presentation.ui.views.LoadingCard
import com.asmaa.khb.foodmenuapp.presentation.ui.views.MainErrorView
import com.asmaa.khb.foodmenuapp.presentation.ui.views.MainLoadingView
import com.asmaa.khb.foodmenuapp.presentation.ui.views.SearchBarView
import com.asmaa.khb.foodmenuapp.presentation.ui.views.TabRowView
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScreenTable(modifier: Modifier = Modifier, viewModel: TableViewModel = koinViewModel()) {
    Box {
        ContentListView(modifier = modifier.fillMaxSize(), viewModel)
        OrderViewBar(modifier = modifier.align(Alignment.BottomCenter), viewModel)
    }
}

/*
  * ###################################################
  * ############# THE MAIN SCREEN LIST VIEWS ##########
  * ####################################################
  * (this composable is to show search-view, tabs-view and grid-view card)
 */
@Composable
fun ContentListView(modifier: Modifier, viewModel: TableViewModel) {
    when (val categoriesState = viewModel.categoriesUiState.collectAsState().value) {
        is CategoriesUiState.Loading -> {
            MainLoadingView()
        }

        is CategoriesUiState.Success -> {
            EndlessLazyColumn(modifier, categoriesState.categories, viewModel)
        }

        is CategoriesUiState.Error -> {
            if (categoriesState.cachedCategories.isNotEmpty()) {
                EndlessLazyColumn(modifier, categoriesState.cachedCategories, viewModel)
            } else {
                MainErrorView(errorMessage = categoriesState.message,
                    onRetry = { viewModel.handleIntent(TableIntent.FetchCategories) })
            }
        }
    }
}


@Composable
fun EndlessLazyColumn(modifier: Modifier, categoryList: List<Category>, viewModel: TableViewModel) {
    LaunchedEffect(Unit) {
        viewModel.handleIntent(TableIntent.FetchProducts(categoryList.first().id))
    }

    val productsState = viewModel.productsUiStateFlow.collectAsState()

    EndlessLazyColumn(modifier = modifier,
        items = productsState.value.list,
        itemKey = { card: Product -> card.id },
        loading = productsState.value.loading,
        searchViewItem = { SearchBarView() },
        tabsViewItem = { TabRowView(categoryList, viewModel) },
        itemsContent = { card: Product ->
            CardView(product = card, viewModel = viewModel)
        },
        loadingItem = { LoadingCard() }) {
        viewModel.loadNextPage()
    }
}

/*
  * ###################################################
  * ################ ORDER VIEW BAR ###################
  * ####################################################
  * (this view appears only when user start selecting items from table)
 */
@Composable
fun OrderViewBar(modifier: Modifier, viewModel: TableViewModel) {
    val orderState = viewModel.orderViewState.collectAsState().value
    if (orderState is OrderViewState.Data) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(xsmallPadding)
                .height(smallViewHeight)
                .background(
                    color = MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.small
                )
                .pointerInput(Unit) { detectTapGestures(onLongPress = { viewModel.resetOrderViewState() }) }) {
            Box(
                modifier = Modifier
                    .padding(start = xlargePadding, end = xlargePadding)
                    .size(mediumIconSize)
                    .background(color = MaterialTheme.colorScheme.onSecondary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = orderState.itemCount.formatSingleDigitNumber(),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Text(
                text = stringResource(id = R.string.view_order_label),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(
                    id = R.string.currency_label, orderState.totalPrice.formatFloatNumber()
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Icon(
                imageVector = Icons.Filled.ArrowForward,
                modifier = Modifier
                    .padding(start = xlargePadding, end = xlargePadding)
                    .size(smallIconSize),
                contentDescription = "ArrowForward Icon",
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}