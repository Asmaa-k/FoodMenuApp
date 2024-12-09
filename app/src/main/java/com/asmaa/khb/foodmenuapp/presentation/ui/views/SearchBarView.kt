package com.asmaa.khb.foodmenuapp.presentation.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.asmaa.khb.foodmenuapp.R
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.mediumViewHeight
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.xlargePadding
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.xsmallIconSize
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.zeroPadding
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.TableViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarView(modifier: Modifier = Modifier, viewModel: TableViewModel = koinViewModel()) {
    val query by viewModel.query.collectAsState()
    Box(
        modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(bottom = xlargePadding)
    ) {
        SearchBar(
            query = query,
            active = false,
            onActiveChange = { viewModel.updateSearchActiveState(it) },
            onSearch = { },
            onQueryChange = {
                viewModel.onQueryChange(it)
            },
            modifier = modifier
                .height(mediumViewHeight)
                .fillMaxWidth()
                .padding(
                    start = xlargePadding,
                    top = zeroPadding,
                    bottom = zeroPadding,
                    end = xlargePadding
                )
                .background(color = MaterialTheme.colorScheme.primary)
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.medium
                ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_label),
                    style = MaterialTheme.typography.bodySmall,
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    modifier = Modifier.size(xsmallIconSize),
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            content = {},
        )
    }
}