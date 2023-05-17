package com.kaiku.cryptospot.data.remote.dto.crypto_list

data class CryptoListResponse(
    val data: List<CryptoListingDataDto>,
    val status: StatusDto
)