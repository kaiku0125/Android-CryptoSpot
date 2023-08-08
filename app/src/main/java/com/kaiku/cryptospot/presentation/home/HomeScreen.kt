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
import com.kaiku.cryptospot.navigation.PocketHomeworkDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.navigation.TestDestination
import com.kaiku.cryptospot.presentation.theme.text_15sp
import com.kaiku.cryptospot.presentation.theme.text_16ssp_bold
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
                modifier = Modifier.padding(start = 20.dp, top = 10.dp),
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
                    text = "Nav bundle測試",
                    style = MaterialTheme.typography.text_15sp
                )
            }

            Button(
                modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                onClick = {
                    ScreenNavigator.to(PocketHomeworkDestination.route)
                }
            ) {
                Text(
                    text = "Tab與Spinner實作",
                    style = MaterialTheme.typography.text_16ssp_bold
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