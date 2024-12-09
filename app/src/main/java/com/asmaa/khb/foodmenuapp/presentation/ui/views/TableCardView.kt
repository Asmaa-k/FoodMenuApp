package com.asmaa.khb.foodmenuapp.presentation.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.asmaa.khb.foodmenuapp.data.entities.Product
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.RedSelection
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.largePadding
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.largeViewHeight
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.mediumCardElevation
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.mediumPadding
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.minusSmallOffset
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.smallViewHeight
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.xsmallIconSize
import com.asmaa.khb.foodmenuapp.presentation.ui.utils.formatSingleDigitNumber
import com.asmaa.khb.foodmenuapp.presentation.ui.utils.intGreaterThanChecker
import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.TableViewModel


@Composable
fun CardView(modifier: Modifier = Modifier, product: Product, viewModel: TableViewModel) {
    val image = remember { product.image } /* cache the image to avoid recomposition */

    Box {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(largeViewHeight)
                .padding(mediumPadding)
                .clickable { viewModel.selectProductItem(product) },
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(mediumCardElevation)
        ) {
            Column(modifier = modifier.fillMaxSize()) {
                AsyncImage(
                    model = image,
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.onBackground),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.Start)
                        .padding(largePadding)
                )
            }
        }

        if (product.selectedQuantity.intGreaterThanChecker()) {
            SelectedCircleRedView(product)
        }
    }
}

@Composable
fun BoxScope.SelectedCircleRedView(product: Product) {
    Box(
        modifier = Modifier
            .size(xsmallIconSize)
            .offset(x = minusSmallOffset, y = minusSmallOffset)
            .background(color = RedSelection, shape = CircleShape)
            .align(Alignment.TopEnd),
    ) {
        Text(
            text = product.selectedQuantity.formatSingleDigitNumber(),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.Center),
            maxLines = 1
        )
    }
}

@Composable
fun LoadingCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(mediumPadding)
            .height(largeViewHeight)
            .fillMaxWidth()
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(smallViewHeight),
                trackColor = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}