package com.kaiku.cryptospot.domain.repository

import com.kaiku.cryptospot.data.remote.dto.crypto_list.CryptoListResponse

interface MainRepository {

    suspend fun requestCryptoList(): List<String>

    suspend fun requestCryptoList2() : CryptoListResponse

    suspend fun requestObserverList(symbol: String)
}