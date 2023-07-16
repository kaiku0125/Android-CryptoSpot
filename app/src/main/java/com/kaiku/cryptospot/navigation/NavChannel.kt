package com.kaiku.cryptospot.navigation

import com.kaiku.cryptospot.navigation.data.NavIntent
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

internal object NavChannel {
    private val channel = Channel<NavIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    internal var navChannel = channel.receiveAsFlow()

    internal fun navigate(destination: NavIntent) {
        channel.trySend(destination)
    }


}