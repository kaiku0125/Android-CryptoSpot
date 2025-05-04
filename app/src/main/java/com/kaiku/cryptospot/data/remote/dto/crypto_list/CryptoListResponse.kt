package com.kaiku.cryptospot.data.remote.dto.crypto_list

import com.kaiku.cryptospot.data.remote.dto.StatusDto

data class CryptoListResponse(
    val data: List<CryptoListingDataDto>,
    val status: StatusDto
)