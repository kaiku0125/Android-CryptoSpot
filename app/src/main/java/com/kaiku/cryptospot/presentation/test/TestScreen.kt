package com.kaiku.cryptospot.presentation.test

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.circular.CircularPercentageComponent
import com.kaiku.cryptospot.customView.circular.CircularPercentageConfig
import com.kaiku.cryptospot.customView.dialog.EdmDialog
import com.kaiku.cryptospot.customView.keyboard.DigitalKeyboardComponent
import com.kaiku.cryptospot.customView.topappbar.ScaffoldTopAppBarWithBackNavComponent
import com.kaiku.cryptospot.navigation.FlashUIDestination
import com.kaiku.cryptospot.navigation.HomeDestination
import com.kaiku.cryptospot.navigation.PocketHomeworkDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.presentation.theme.text_15sp_400weight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TestScreen() {

    Scaffold(
        topBar = {
            ScaffoldTopAppBarWithBackNavComponent(
                titleText = "測試區域",
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                },
                onNavigationClick = {
                    ScreenNavigator.back(
                        route = HomeDestination.route
                    )
                }
            )
        }
    ) { innerPadding ->
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            ReorderGrid()
            Button(
                modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                onClick = {
                    ScreenNavigator.to(
                        FlashUIDestination(
                            invokeString = "nav bundle string",
                            invokeInt = 567,
                            invokeBoolean = false
                        )
                    )
                }
            ) {
                Text(
                    text = "Nav bundle測試",
                    style = MaterialTheme.typography.text_15sp_400weight
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
                    style = MaterialTheme.typography.text_15sp_400weight
                )
            }

            val showDialog = remember { mutableStateOf(false) }
            if (showDialog.value) {
                EdmDialog(
                    onNotRemind = {},
                    onDismiss = { showDialog.value = false }
                )
            }
            Button(
                modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                onClick = {
                    showDialog.value = true
                }
            ) {
                Text(
                    text = "Dialog測試",
                    style = MaterialTheme.typography.text_15sp_400weight
                )
            }

            val sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            )

            val keyboardController = LocalSoftwareKeyboardController.current

            var isSheetOpen by rememberSaveable {
                mutableStateOf(false)
            }

            val text = remember { mutableStateOf("54879487") }
            TextField(
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp)
                    .clickable(
                        onClick = {
                            keyboardController?.hide()
                            isSheetOpen = true
                        }
                    ),
                enabled = false,
                value = text.value,
                onValueChange = {}
            )

            if (isSheetOpen) {
                DigitalKeyboardComponent(
                    sheetState = sheetState,
                    onConfirm = {
                        scope.launch {
                            sheetState.hide()
                            isSheetOpen = false
                        }
                        text.value = it
                    },
                    onDismissRequest = {
                        isSheetOpen = false
                    }
                )
            }

            CircularPercentageComponent(
                config = CircularPercentageConfig(
                    percentage = 0.8f
                )
            )

        }

    }
}