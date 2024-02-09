package com.kaiku.cryptospot.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.icon.SimpleIconButton
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.customView.topappbar.ScaffoldTopAppBarWithBackNavComponent
import com.kaiku.cryptospot.navigation.FindCryptoDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.navigation.TestDestination
import com.kaiku.cryptospot.presentation.MainActivity
import com.kaiku.cryptospot.presentation.home.data.HomeViewAction
import com.kaiku.cryptospot.presentation.theme.CryptoSpotTheme

@Composable
fun HomeScreenRoot() {
    val context = LocalContext.current

    HomeScreen(
        action = { action ->
            when(action) {
                is HomeViewAction.NavigateAction -> {
                    ScreenNavigator.to(action.destination)
                }

                is HomeViewAction.FinishAction -> {
                    (context as MainActivity).finish()
                }
            }
        }
    )
}

@Composable
private fun HomeScreen(
    action: (HomeViewAction) -> Unit
) {
    Scaffold(
        topBar = {
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
                        onIconClick = {
                            action.invoke(
                                HomeViewAction.NavigateAction(
                                    destination = FindCryptoDestination.route
                                )
                            )
                        }
                    )
                },
                onNavigationClick = {
                    action.invoke(
                        HomeViewAction.FinishAction
                    )
                }
            )
        }
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            Button(
                onClick = {
                    ScreenNavigator.to(
                        route = TestDestination.route
                    )
                }
            ) {
                Text(text = "進入測試")
            }
        }

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    CryptoSpotTheme(darkTheme = true) {
        HomeScreen(
            action = {}
        )
    }
}