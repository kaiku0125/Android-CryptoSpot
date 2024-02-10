package com.kaiku.cryptospot.presentation.crypto_list

import androidx.paging.compose.LazyPagingItems
import com.kaiku.cryptospot.domain.model.CryptoListingData

data class CryptoListState(
    val isLoading : Boolean = false,
    val cryptoList : List<CryptoListingData> = emptyList(),
    val errorMsg : String = ""
)
