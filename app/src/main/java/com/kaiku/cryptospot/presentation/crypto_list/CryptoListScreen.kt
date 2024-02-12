package com.kaiku.cryptospot.presentation.crypto_list

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.kaiku.cryptospot.customView.loading.CircularProgressLoader
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.customView.topappbar.ScaffoldTopAppBarWithBackNavComponent
import com.kaiku.cryptospot.domain.model.CryptoListingData
import com.kaiku.cryptospot.navigation.HomeDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.presentation.theme.CryptoSpotTheme
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun CryptoListScreenRoot(
    viewModel: CryptoListViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    val cryptos = viewModel.pagerFlow.collectAsLazyPagingItems()

    CryptoListScreen(
        viewState = viewState,
        cryptos = cryptos,
        onLoading = {
            viewModel.onLoading()
        }
    )
}


// TODO: Screen rotate maybe failed
@Composable
private fun CryptoListScreen(
    viewState: CryptoListState,
    cryptos: LazyPagingItems<CryptoListingData>,
    onLoading: () -> Unit
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

        LazyPagingCrypto(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            viewState = viewState,
            cryptos = cryptos,
            onLoading = onLoading
        )

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LazyPagingCrypto(
    modifier: Modifier = Modifier,
    viewState: CryptoListState,
    cryptos: LazyPagingItems<CryptoListingData>,
    onLoading: () -> Unit
) {
    val context = LocalContext.current
    val refreshState = rememberPullRefreshState(
        refreshing = viewState.isLoading,
        onRefresh = {
            cryptos.refresh()
        }
    )
    LaunchedEffect(cryptos.loadState) {
        when(cryptos.loadState.refresh) {
            is LoadState.Error -> {
                Toast.makeText(
                    context,
                    "載入失敗",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {
                onLoading.invoke()
            }
        }
    }

    Box(
        modifier = modifier.pullRefresh(
            refreshState,
            !viewState.isLoading
        )
    ) {
        Crossfade(
            targetState = cryptos.loadState.refresh,
            label = "isLoading"
        ) { state ->
            when(state){
                is LoadState.Loading -> {
                    CircularProgressLoader()
                }

                else -> {
                    LazyColumn(
                        state = rememberLazyListState()
                    ) {
                        items(
                            count = cryptos.itemCount,
                            key = { index -> cryptos[index]?.id ?: index },
                            contentType = cryptos.itemContentType { "content_type" }
                        ) { index ->
                            val item = cryptos[index]
                            item?.let {
                                InfoItem(
                                    rank = it.rank,
                                    info = it.symbol
                                )
                            }
                        }
                        item {
                            if (cryptos.loadState.append.endOfPaginationReached) {
                                SimpleText(
                                    modifier = Modifier.fillMaxWidth(),
                                    config = SimpleTextConfig(
                                        value = "沒有更多資料了"
                                    )
                                )
                            }

                            if (cryptos.loadState.append is LoadState.Loading) {
                                CircularProgressLoader()
                            }
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = viewState.isLoading,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

}

@Composable
fun InfoItem(rank: Int, info: String) {
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
            Text(text = "排行 : $rank", fontWeight = FontWeight.Bold)
            Text(text = "幣種 -> $info")
        }
    }
}

@Preview
@Composable
private fun CryptoListScreenPreview() {
    CryptoSpotTheme(darkTheme = true) {
        val list = listOf(
            CryptoListingData(
                rank = 1,
                id = 123,
                symbol = "BTC",
                price = 50000.0
            ),
            CryptoListingData(
                rank = 2,
                id = 234,
                symbol = "ETH",
                price = 3000.0
            ),
            CryptoListingData(
                rank = 3,
                id = 345,
                symbol = "BNB",
                price = 500.0
            )
        )
        val lazyItems = flowOf(
            PagingData.from(
                data = list,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                ),
            )
        ).collectAsLazyPagingItems()

        CryptoListScreen(
            viewState = CryptoListState(
                isLoading = false,
                cryptoList = list,
            ),
            cryptos = lazyItems,
            onLoading = {

            }
        )
    }
}
