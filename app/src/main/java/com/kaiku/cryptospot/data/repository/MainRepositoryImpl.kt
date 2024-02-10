package com.kaiku.cryptospot.data.repository

import com.kaiku.cryptospot.data.remote.CoinMarketCapApi
import com.kaiku.cryptospot.data.remote.dto.crypto_list.CryptoListResponse
import com.kaiku.cryptospot.domain.repository.MainRepository
import timber.log.Timber

class MainRepositoryImpl(
    private val api: CoinMarketCapApi
) : MainRepository{

//    suspend fun requestCryptoList(): List<String> {
//
//        CoroutineScope(Dispatchers.IO).launch {
//
//            val response = api.getCryptoListings().awaitResponse()
//
//            if (response.isSuccessful) {
//                val body = response.body()!!
//                Log.e(TAG, "onResponse: body ➔ ${body.toString()}")
//
//                val cryptoListings = body.data
//                val cryptoSymbols = cryptoListings.map { it.symbol }
//
//                cryptoList = cryptoSymbols
//                Log.e(TAG, "getList: symbols ➔ $cryptoSymbols , size ➔ ${cryptoSymbols.size}")
//            }
//
//        }
//        Log.e(TAG, "requestCryptoList: end scope")
//        return cryptoList
//    }

//    suspend fun requestCryptoList(): List<String> {
//        Timber.e("Method start")
//        val response = api.getCryptoListings()
//
//        if (response.isSuccessful) {
//            val body = response.body()!!
//            Timber.e("onResponse: body ➔ ${body.toString()}")
//
//            val cryptoListings = body.data
//            val cryptoSymbols = cryptoListings.map { it.symbol }
//
//            cryptoList = cryptoSymbols
//            Timber.e("getList: symbols ➔ $cryptoSymbols , size ➔ ${cryptoSymbols.size}")
//        }
//
//        Timber.e("Method end")
//        return cryptoList
//    }

    override suspend fun requestCryptoList(): CryptoListResponse {
        return api.getCryptoListings(
            start = 1,
            limit = 10
        )
    }

    override suspend fun requestObserverList(symbol: String) {

    }

}