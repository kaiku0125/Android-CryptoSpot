package com.kaiku.cryptospot.presentation.crypto_list

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.github.fengdai.compose.pulltorefresh.PullToRefresh
import com.github.fengdai.compose.pulltorefresh.rememberPullToRefreshState
import com.kaiku.composecomponent.LocalProvider
import com.kaiku.composecomponent.component.loading.PocketPullRefreshIndicator
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.utils.isPreviewMode
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text14Sp
import com.kaiku.composecomponent.utils.text16Sp
import com.kaiku.cryptospot.customView.loading.CircularProgressLoader
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.customView.topappbar.ScaffoldTopAppBarWithBackNavComponent
import com.kaiku.cryptospot.domain.model.CryptoListingData
import com.kaiku.cryptospot.navigation.HomeDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.presentation.crypto_list.data.CryptoListViewAction
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
        action = { action ->
            viewModel.dispatch(action = action)
        }
    )
}


// TODO: Screen rotate maybe failed
@Composable
private fun CryptoListScreen(
    viewState: CryptoListState,
    cryptos: LazyPagingItems<CryptoListingData>,
    action: (CryptoListViewAction) -> Unit
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
            action = action
        )

    }

}

@Composable
private fun LazyPagingCrypto(
    modifier: Modifier = Modifier,
    viewState: CryptoListState,
    cryptos: LazyPagingItems<CryptoListingData>,
    action: (CryptoListViewAction) -> Unit
) {
    val context = LocalContext.current
    var isByPull by remember { mutableStateOf(false) }

    val isPullRefreshing by remember(cryptos.loadState.refresh, isByPull) {
        derivedStateOf {
            (cryptos.loadState.refresh is LoadState.Loading) && isByPull
        }
    }

    LaunchedEffect(cryptos.loadState.refresh) {
        when (cryptos.loadState.refresh) {
            is LoadState.Loading -> {
//                Timber.tag("wtf").e("[refresh] loading...")
            }

            is LoadState.NotLoading -> {
//                Timber.tag("wtf").e("[refresh] NotLoading")
                isByPull = false
            }

            is LoadState.Error -> {
//                Timber.tag("wtf").e("[refresh] error")
                Toast.makeText(
                    context,
                    "載入失敗",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

//    LaunchedEffect(cryptos.loadState.append) {
//        when(cryptos.loadState.append) {
//            is LoadState.Loading -> {
//                Timber.tag("wtf").e("[append] Loading")
//            }
//
//            is LoadState.NotLoading -> {
//                Timber.tag("wtf").e("[append] NotLoading")
//            }
//
//            is LoadState.Error -> {
//                Timber.tag("wtf").e("[append] error")
//            }
//        }
//    }


    PullToRefresh(
        modifier = modifier,
        enabled = isPullRefreshing.not() && cryptos.loadState.append !is LoadState.Loading,
        state = rememberPullToRefreshState(isRefreshing = isPullRefreshing),
        onRefresh = {
            cryptos.refresh()
            isByPull = true
        },
        indicator = { s, trigger, offset ->
            PocketPullRefreshIndicator(
                state = s,
                refreshTriggerDistance = trigger,
                refreshingOffset = offset
            )
        },
        content = {
            Crossfade(
                targetState = if (isPreviewMode()) {
                    cryptos.loadState.append
                } else {
                    cryptos.loadState.refresh
                },
                label = "isLoading"
            ) { state ->
                when (state) {
                    is LoadState.Loading -> {
                        if (isPullRefreshing.not()) {
                            CircularProgressLoader()
                        }
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
        }
    )

}

@Composable
fun InfoItem(rank: Int, info: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.sdp(), horizontal = 4.sdp()),
        shape = RoundedCornerShape(8.sdp()),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.sdp()
        )
    ) {
        Column(modifier = Modifier.pocketPadding(all = 16)) {
            PocketText(
                config = PocketTextConfig(
                    value = "排行 : $rank",
                    style = text16Sp(600)
                )

            )
            PocketSpacer(height = 4)
            PocketText(
                config = PocketTextConfig(
                    value = "幣種 -> $info",
                    style = text14Sp()
                )

            )
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

        CompositionLocalProvider(LocalProvider.LocalSpacerIndicator provides false) {
            CryptoListScreen(
                viewState = CryptoListState(
                    isLoading = false,
                    cryptoList = list,
                ),
                cryptos = lazyItems,
                action = {

                }
            )
        }
    }
}
