package com.kaiku.cryptospot.customView.topappbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.R
import com.kaiku.cryptospot.customView.icon.SimpleIconButton
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.customView.text.TextWithIcon
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.navigation.FindCryptoDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.presentation.MainActivity
import com.kaiku.cryptospot.presentation.theme.CryptoSpotTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldTopAppBarWithBackNavComponent(
    modifier: Modifier = Modifier,
    appBarBackground: Color = MaterialTheme.colorScheme.background,
    textConfig: SimpleTextConfig,
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
                    drawableSize = 20.dp,
                    content = {
                        SimpleText(config = textConfig)
                    }
                )
            } else {
                SimpleText(config = textConfig)
            }
        },
        navigationIcon = {
            SimpleIconButton(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp),
                imageVector = Icons.Default.ArrowBack,
                iconSize = 24.dp,
                onIconClick = {
                    onNavigationClick()
                }
            )
        },
        actions = actions,
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = appBarBackground)
    )
}

@Preview
@Composable
private fun ScaffoldTopAppBarWithBackNavComponentPreview() {
    CryptoSpotTheme(darkTheme = true) {
        ScaffoldTopAppBarWithBackNavComponent(
            textConfig = SimpleTextConfig(
                value = "現貨損益",
                textColor = Color.Unspecified,
                style = MaterialTheme.typography.titleLarge
            ),
            actions = {
                SimpleIconButton(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(24.dp),
                    imageVector = Icons.Default.Add,
                    iconSize = 24.dp,
                    onIconClick = {}
                )
            },
            onNavigationClick = {}
        )
    }
}

