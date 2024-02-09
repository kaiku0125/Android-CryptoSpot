package com.kaiku.cryptospot.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    onEvent: (T) -> Unit
) {
    val owner = LocalLifecycleOwner.current
    LaunchedEffect(flow, owner.lifecycle) {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest { onEvent.invoke(it) }
        }
    }
}