package com.kaiku.cryptospot.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.navigation.FindCryptoDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.navigation.TestDestination
import com.kaiku.cryptospot.presentation.theme.figma_12ssp
import com.kaiku.cryptospot.presentation.theme.figma_15ssp
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Timber.e("Now in home screen")
    Scaffold(
        topBar = { MyTopBar() }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Button(
                modifier = Modifier.padding(start = 20.dp, top = 50.dp),
                onClick = {
                    ScreenNavigator.to(
                        TestDestination(
                            invokeString = "nav bundle string",
                            invokeInt = 567,
                            invokeBoolean = false
                        )
                    )
                }
            ) {
                Text(
                    text = "test",
                    style = MaterialTheme.typography.figma_12ssp
                )
            }

            Button(
                modifier = Modifier.padding(start = 20.dp, top = 30.dp),
                onClick = {

                }
            ) {
                Text(
                    text = "figma_15ssp",
                    style = MaterialTheme.typography.figma_15ssp
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "現貨損益",
            )

        },
        actions = {
            IconButton(
                onClick = {
                    ScreenNavigator.to(FindCryptoDestination.route)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
}