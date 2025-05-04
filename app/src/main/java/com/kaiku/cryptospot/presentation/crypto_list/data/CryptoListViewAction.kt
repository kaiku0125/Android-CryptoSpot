package com.kaiku.cryptospot.presentation.crypto_list.data

import com.kaiku.cryptospot.domain.model.CryptoListingData
import com.kaiku.cryptospot.domain.model.Holding

sealed class CryptoListViewAction {

    data class OnCardClickAction(
        val data: CryptoListingData
    ): CryptoListViewAction()

    data object ResetDialogAction: CryptoListViewAction()

    data class AddHoldingAction(
        val holding: Holding
    ): CryptoListViewAction()

    data class InputSearchTextAction(
        val text: String
    ): CryptoListViewAction()
}