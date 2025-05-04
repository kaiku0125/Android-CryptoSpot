package com.kaiku.cryptospot.presentation.crypto_list

import com.kaiku.cryptospot.domain.model.CryptoListingData

data class CryptoListViewState(
    val searchText: String = "",
    val cryptoList : List<CryptoListingData> = emptyList(),
) {
    companion object {
        val INIT by lazy {
            CryptoListViewState()
        }
    }
}
