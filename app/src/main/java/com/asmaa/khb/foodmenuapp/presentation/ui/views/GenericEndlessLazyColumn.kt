package com.asmaa.khb.foodmenuapp.presentation.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/*
* This Generic LazyVerticalGrid called EndlessLazyColumn
* Used to handel pagination
* Also to contain all the main components including the headers (tabs|search),
* to avoid the nested-scrolling problem between normal-views and lazy-lists views
 */
@Composable
internal fun <T> EndlessLazyColumn(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    listState: LazyGridState = rememberLazyGridState(),
    items: List<T>,
    itemKey: (T) -> Any,
    searchViewItem: @Composable () -> Unit,
    tabsViewItem: @Composable () -> Unit,
    itemsContent: @Composable (T) -> Unit,
    loadingItem: @Composable () -> Unit,
    loadMore: () -> Unit
) {

    val reachedBottom: Boolean by remember {
        derivedStateOf { listState.reachedBottom() }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) loadMore()
    }

    LazyVerticalGrid(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        columns = GridCells.Adaptive(100.dp),
        state = listState
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            searchViewItem()
        }

        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            tabsViewItem()
        }

        items(
            items = items,
            key = { item: T -> itemKey(item) },
        ) { item ->
            itemsContent(item)
        }

        if (loading) {
            item {
                loadingItem()
            }
        }
    }
}

internal fun LazyGridState.reachedBottom(): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index == (this.layoutInfo.totalItemsCount - 1)
}