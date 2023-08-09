package com.kaiku.cryptospot.customView.topappbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.R
import com.kaiku.cryptospot.customView.text.TextWithIcon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldTopAppBarWithBackNavComponent(
    modifier: Modifier = Modifier,
    appBarBackground: Color = MaterialTheme.colorScheme.background,
    titleText: String = "",
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
    needInformation: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    onNavigationClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            if (needInformation) {
                TextWithIcon(
                    drawableEnd = R.drawable.information,
                    drawableSize = 20.dp
                ) {
                    Text(
                        text = titleText,
                        style = titleStyle
                    )
                }
            } else {
                Text(
                    text = titleText,
                    style = titleStyle
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = { onNavigationClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = appBarBackground)
    )
}

