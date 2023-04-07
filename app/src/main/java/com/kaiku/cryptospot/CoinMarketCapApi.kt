package com.kaiku.cryptospot

import com.kaiku.cryptospot.data.CoinMarketCapResponse
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
    fun getCurrentPrice(@Query("symbol") symbol: String): Call<CoinMarketCapResponse>
}