package com.kaiku.cryptospot.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.kaiku.cryptospot.customView.topappbar.ScaffoldTopAppBarWithBackNavComponent
import com.kaiku.cryptospot.navigation.FindCryptoDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.navigation.TestDestination
import com.kaiku.cryptospot.presentation.MainActivity

@Composable
fun HomeScreen() {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            ScaffoldTopAppBarWithBackNavComponent(
                titleText = "現貨損益",
                actions = {
                    IconButton(
                        onClick = {
                            ScreenNavigator.to(FindCryptoDestination.route)
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                },
                onNavigationClick = {
                    (context as MainActivity).finish()
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