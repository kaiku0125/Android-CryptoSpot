package com.kaiku.cryptospot.data.remote

import com.kaiku.cryptospot.data.remote.dto.crypto_list.CryptoListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinMarketCapApi {

    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getCryptoListings(
        @Query("start") start: Int = 1,
        @Query("limit") limit: Int = 10,
    ): CryptoListResponse

}