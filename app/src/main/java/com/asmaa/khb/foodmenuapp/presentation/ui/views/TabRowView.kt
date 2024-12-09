package com.asmaa.khb.foodmenuapp.presentation.ui.views


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.asmaa.khb.foodmenuapp.data.entities.Category
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.xsmallViewHeight
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.zeroPadding
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.TableViewModel


@Composable
fun TabRowView(categories: List<Category>, viewModel: TableViewModel) {
    CustomScrollableTabRow(
        categories = categories,
        selectedTabIndex = viewModel.selectedTabIndex.value
    ) { tabIndex, tab ->
        viewModel.onCategoryTabClick(tab, tabIndex)
    }
}


@Composable
fun CustomScrollableTabRow(
    categories: List<Category>, selectedTabIndex: Int, onTabClick: (Int, Category) -> Unit
) {
    val density = LocalDensity.current
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(categories.size) {
            tabWidthStateList.add(zeroPadding)
        }
        tabWidthStateList
    }
    ScrollableTabRow(
        modifier = Modifier.wrapContentHeight(),
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.surface,
        edgePadding = zeroPadding,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = MaterialTheme.colorScheme.secondary,
                height = 2.dp,
                modifier = Modifier.customTabIndicatorOffset(
                    currentTabPosition = tabPositions[selectedTabIndex],
                    tabWidth = tabWidths[selectedTabIndex],
                )
            )
        },
        divider = {
            Divider(color = Color.Transparent)
        }
    ) {
        categories.forEachIndexed { tabIndex, tab ->
            Tab(
                modifier = Modifier.height(xsmallViewHeight),
                selected = selectedTabIndex == tabIndex,
                onClick = { onTabClick(tabIndex, tab) },
                text = {
                    Text(
                        text = tab.name,
                        style = if (selectedTabIndex == tabIndex) MaterialTheme.typography.titleSmall else MaterialTheme.typography.labelSmall,
                        onTextLayout = { textLayoutResult ->
                            tabWidths[tabIndex] =
                                with(density) { textLayoutResult.size.width.toDp() }
                        }
                    )
                }
            )
        }
    }
}

fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}