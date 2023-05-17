package com.kaiku.cryptospot.data.remote

import com.kaiku.cryptospot.data.remote.dto.crypto_list.CryptoListResponse
import com.kaiku.cryptospot.domain.model.CoinMarketCapResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinMarketCapApi {
    @GET("v1/cryptocurrency/quotes/latest")
    suspend fun getBitcoinPrice(
        @Query("symbol") symbol: String = "BTC",
        @Query("convert") convert: String = "USD"
    ): CoinMarketCapResponse

    @GET("v1/cryptocurrency/quotes/latest")
    fun getCurrentPrice(
        @Query("symbol") symbol: String
    ): Call<CoinMarketCapResponse>

    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getCryptoListings(
        @Query("start") start: Int = 1,
        @Query("limit") limit: Int = 10,
//        @Query("convert") convert: String = "USD"
    ): CryptoListResponse

}