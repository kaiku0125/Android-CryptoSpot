package com.kaiku.cryptospot.data

data class CoinMarketCapResponse2(
    val data: List<CryptoListing>,
    val status: CoinMarketCapStatus,
)

data class CryptoListing(
    val id: Int,
    val name: String,
    val symbol: String,
    // add other properties as needed
)

data class CoinMarketCapStatus(
    val error_code: Int?,
    val error_message: String?,
    val credit_count: Int
)