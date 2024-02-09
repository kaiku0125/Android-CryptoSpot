package com.kaiku.cryptospot.presentation.crypto_list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaiku.cryptospot.customView.loading.CircularProgressLoader
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.customView.topappbar.ScaffoldTopAppBarWithBackNavComponent
import com.kaiku.cryptospot.domain.model.CryptoListingData
import com.kaiku.cryptospot.navigation.HomeDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.presentation.theme.CryptoSpotTheme
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun CryptoListScreenRoot(
    viewModel: CryptoListViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    CryptoListScreen(
        viewState = viewState
    )
}


// TODO: Screen rotate maybe failed
@Composable
private fun CryptoListScreen(
    viewState: CryptoListState
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ScaffoldTopAppBarWithBackNavComponent(
                textConfig = SimpleTextConfig(
                    value = "添加觀察幣種",
                    textColor = Color.Unspecified,
                    style = MaterialTheme.typography.titleLarge
                ),
                onNavigationClick = {
                    ScreenNavigator.back(HomeDestination.route)
                }
            )
        }
    ) { paddingValues ->

        Crossfade(
            targetState = viewState.isLoading,
            label = "isLoading"
        ) { isLoading ->
            if (isLoading) {
                CircularProgressLoader()
            } else {
                if (viewState.errorMsg.isNotBlank()) {
                    SimpleText(
                        modifier = Modifier.fillMaxSize(),
                        config = SimpleTextConfig(
                            value = viewState.errorMsg.trim()
                        )
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        itemsIndexed(viewState.cryptoList) { index, info ->
                            InfoItem(
                                index = index,
                                info = info.symbol
                            )
                        }
                    }
                }
            }
        }

    }

}

@Composable
fun InfoItem(index: Int, info: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "編號 : ${(index + 1).toString()}", fontWeight = FontWeight.Bold)
            Text(text = "幣 -> ${info.toString()}")
        }
    }
}

@Preview
@Composable
private fun CryptoListScreenPreview() {
    CryptoSpotTheme(darkTheme = true) {
        CryptoListScreen(
            viewState = CryptoListState(
                cryptoList = listOf(
                    CryptoListingData(
                        id = 1,
                        symbol = "BTC"
                    ),
                    CryptoListingData(
                        id = 2,
                        symbol = "ETH",
                    ),
                    CryptoListingData(
                        id = 3,
                        symbol = "BNB",
                    )
                )
            )
        )
    }
}
