package com.kaiku.cryptospot.domain.model

data class CryptoListingData(
    val rank: Int,
    val id: Int,
    val symbol: String,
    val price: Double,
)
