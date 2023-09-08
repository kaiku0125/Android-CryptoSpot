package com.kaiku.cryptospot.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.dialog.CustomDialog
import com.kaiku.cryptospot.customView.dialog.EdmDialog
import com.kaiku.cryptospot.customView.keyboard.DigitalKeyboardComponent
import com.kaiku.cryptospot.customView.topappbar.ScaffoldTopAppBarWithBackNavComponent
import com.kaiku.cryptospot.navigation.FindCryptoDestination
import com.kaiku.cryptospot.navigation.PocketHomeworkDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.navigation.TestDestination
import com.kaiku.cryptospot.presentation.MainActivity
import com.kaiku.cryptospot.presentation.theme.text_15sp
import com.kaiku.cryptospot.presentation.theme.text_16ssp_bold
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen() {
    Timber.e("Now in home screen")
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
                }
            ) {
                (context as MainActivity).finish()
            }
        }
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

            val showDialog = remember { mutableStateOf(false) }
            if (showDialog.value) {
//                CustomDialog(
//                    value = "",
//                    setShowDialog = {
//                        showDialog.value = it
//                    },
//                    setValue = {
//                        Timber.e("set value : $it")
//                    }
//                )
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
                    style = MaterialTheme.typography.text_16ssp_bold
                )
            }

            val scope = rememberCoroutineScope()

            val sheetState = rememberModalBottomSheetState()

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
                onValueChange = {},
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                keyboardActions = KeyboardActions(
//                    onDone = { keyboardController?.hide() }
//                )
            )

            if (isSheetOpen) {
                DigitalKeyboardComponent(
                    text = text.value,
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