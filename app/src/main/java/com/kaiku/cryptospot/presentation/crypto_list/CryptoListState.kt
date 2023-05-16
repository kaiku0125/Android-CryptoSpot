package com.kaiku.cryptospot.presentation.crypto_list

data class CryptoListState(
    val isLoading : Boolean = false,
    val cryptoList : List<String> = emptyList(),
    val error : String = ""

)
