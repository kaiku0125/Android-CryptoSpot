package com.kaiku.cryptospot.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CryptoListingDao {

    @Upsert
    suspend fun upsertAll(cryptos: List<CryptoListingEntity>)

    @Query("SELECT * FROM CryptoListingEntity")
    fun pagingSource(): PagingSource<Int, CryptoListingEntity>

    @Query("DELETE FROM CryptoListingEntity")
    suspend fun deleteAll()
}