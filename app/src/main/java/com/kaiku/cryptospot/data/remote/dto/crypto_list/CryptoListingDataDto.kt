package com.kaiku.cryptospot.data.remote.dto.crypto_list

import com.kaiku.cryptospot.domain.model.CryptoListingData

data class CryptoListingDataDto(
    val id: Int,
    val name: String,
    val symbol: String,
)


fun CryptoListingDataDto.toPoint() : CryptoListingData{
    return CryptoListingData(
        id = id,
        symbol = symbol,
    )
}

