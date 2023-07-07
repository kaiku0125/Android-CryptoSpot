package com.kaiku.cryptospot.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kaiku.cryptospot.presentation.theme.figma_12ssp
import com.kaiku.cryptospot.presentation.theme.figma_15ssp
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController) {
    Timber.e("Now in home view")
    Scaffold(
        topBar = { MyTopBar(nav) }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Button(
                modifier = Modifier.padding(start = 20.dp, top = 50.dp),
                onClick = {
//                nav.navigate("LoginView")
                }
            ) {
                Text(
                    text = "test",
                    style = MaterialTheme.typography.figma_12ssp
                )
            }

            Button(
                modifier = Modifier.padding(start = 20.dp, top = 30.dp),
                onClick = { /*TODO*/ }
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
fun MyTopBar(nav: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "現貨損益",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        },
        modifier = Modifier.fillMaxWidth(),
        actions = {
            IconButton(onClick = { nav.navigate("FindCryptoView") }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
}