package com.kaiku.cryptospot.domain.model

data class CryptoListingData(
    val rank: Int,
    val id: Int,
    val symbol: String,
    val price: Double,
) {
    companion object {
        val PREVIEW = CryptoListingData(
            rank = 1,
            id = 1,
            symbol = "BTC",
            price = 94153.68
        )
    }
}
