package com.kaiku.cryptospot.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaiku.cryptospot.navigation.HomeDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.presentation.login.data.LoginViewState
import com.kaiku.cryptospot.presentation.theme.CryptoSpotTheme
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel()
) {

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LoginScreen(
        viewState = viewState,
        onEnterApiKey = {
            viewModel.updateApiKey(it)
        }
    )
}

@Composable
fun LoginScreen(
    viewState: LoginViewState,
    onEnterApiKey: (String) -> Unit
) {
    Scaffold(topBar = { LoginTopBar() }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LoginScreenContent(
                viewState = viewState,
                onEnterApiKey = onEnterApiKey
            )
        }
    }
}

@Composable
fun LoginScreenContent(
    viewState: LoginViewState,
    onEnterApiKey: (String) -> Unit
) {
    var textValue by remember { mutableStateOf(viewState.apiKey) }

    Column {
        TextField(
            value = textValue,
            onValueChange = { newValue ->
                if (newValue.matches("^[a-zA-Z0-9.-]*$".toRegex())) {
                    Timber.e("EnterAPIView: is valid")
                    textValue = newValue
                }
            },
            label = { Text("輸入您的CoinMarketCap API 私鑰") }
        )
        OutlinedButton(
            onClick = {
                Timber.d("User entered: $textValue")
                onEnterApiKey(textValue)
                ScreenNavigator.to(HomeDestination.route)
            },
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("完成")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "輸入您的 API key",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun LoginScreenPreview() {
    CryptoSpotTheme(darkTheme = true) {
        LoginScreen(
            viewState = LoginViewState(),
            onEnterApiKey = {}
        )
    }
}