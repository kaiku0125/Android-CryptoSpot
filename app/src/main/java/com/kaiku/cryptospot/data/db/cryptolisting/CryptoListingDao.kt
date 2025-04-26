package com.kaiku.cryptospot.data.db.cryptolisting

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kaiku.cryptospot.data.remote.CoinMarketCapApi
import com.kaiku.cryptospot.data.remote.dto.crypto_list.toEntity

@Dao
interface CryptoListingDao {

    @Upsert
    suspend fun upsertAll(cryptos: List<CryptoListingEntity>)

    @Query("SELECT * FROM CryptoListingEntity")
    fun pagingSource(): PagingSource<Int, CryptoListingEntity>

    @Query("DELETE FROM CryptoListingEntity")
    suspend fun deleteAll()
}
