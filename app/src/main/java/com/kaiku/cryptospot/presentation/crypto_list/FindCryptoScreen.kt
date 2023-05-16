package com.kaiku.cryptospot.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.kaiku.cryptospot.presentation.crypto_list.CryptoListViewModel
import timber.log.Timber

private const val TAG = "FindCrypto"

// TODO: Screen rotate maybe failed
@Composable
fun FindCryptoScreen(nav: NavController) {
    val viewModel: CryptoListViewModel = hiltViewModel()
    val listState = viewModel.apiCryptoListState.value

    OnLifecycleEvent(onEvent = { owner, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            Timber.e("onResume ....")
            viewModel.refreshCryptoList()
        }
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            itemsIndexed(listState.cryptoList) { index, info ->
                InfoItem(index = index, info = info)
            }
        }

        if (listState.error.isNotBlank()) {
            Snackbar() {
                Text(text = listState.error.trim())
            }
        }

        if (listState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Timber.e("listState is now loading....")
                CircularProgressIndicator()
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
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "編號 : ${(index + 1).toString()}", fontWeight = FontWeight.Bold)
            Text(text = "幣 -> ${info.toString()}")
        }
    }
}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}
