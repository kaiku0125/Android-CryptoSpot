package com.kaiku.cryptospot.presentation.crypto_list.data

sealed class CryptoListViewAction {

    data object OnPullRefreshAction: CryptoListViewAction()

    data object OnLoadedAction: CryptoListViewAction()
}