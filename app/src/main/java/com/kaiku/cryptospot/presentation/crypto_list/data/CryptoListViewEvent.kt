package com.kaiku.cryptospot.presentation.crypto_list.data

import com.kaiku.cryptospot.domain.model.CryptoListingData

sealed class CryptoListViewEvent {

    data object HideDialog: CryptoListViewEvent()

    data class ShowAddUserHoldingsDialog(
        val data: CryptoListingData
    ): CryptoListViewEvent()
}