package com.kaiku.cryptospot.domain.repository

interface MainRepository {

    suspend fun requestCryptoList(): List<String>

    suspend fun requestObserverList(symbol: String)
}