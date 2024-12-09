package com.asmaa.khb.foodmenuapp.presentation.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.asmaa.khb.foodmenuapp.R
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.largePadding
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.mediumIconSize
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.mediumPadding
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.smallPadding
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.xlargePadding
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.xsmallIconSize
import com.asmaa.khb.foodmenuapp.presentation.ui.theme.zeroPadding
import com.asmaa.khb.foodmenuapp.presentation.ui.utils.StaticValues


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar() {
    Column {
        StatusBarView()
        TopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Text(
                    text = stringResource(id = R.string.menu_screen_title),
                    style = MaterialTheme.typography.displayLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* Handle navigation */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        modifier = Modifier.size(mediumIconSize),
                        contentDescription = "Navigation Icon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* Handle action 1 */ }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            modifier = Modifier.size(xsmallIconSize),
                            contentDescription = "Restaurant Icon",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(smallPadding))
                        Text(
                            text = StaticValues.numOfPeople,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                Spacer(modifier = Modifier.width(mediumPadding))

                IconButton(onClick = { /* Handle action 2 */ }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Group,
                            modifier = Modifier.size(xsmallIconSize),
                            contentDescription = "Group Icon",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(smallPadding))
                        Text(
                            text = StaticValues.numOfMeals,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun StatusBarView() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                start = xlargePadding,
                end = xlargePadding,
                top = largePadding,
                bottom = zeroPadding
            )
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            modifier = Modifier
                .padding(end = largePadding)
                .size(xsmallIconSize),
            contentDescription = "Person Icon",
            tint = MaterialTheme.colorScheme.secondary,
        )

        Text(
            text = StaticValues.userName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = StaticValues.userId,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelSmall
        )

        Icon(
            imageVector = Icons.Filled.FiberManualRecord,
            modifier = Modifier
                .padding(start = largePadding)
                .size(xsmallIconSize),
            contentDescription = "FiberManualRecord Icon",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}
