package com.kaiku.cryptospot.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kaiku.cryptospot.data.db.cryptolisting.CryptoListingEntity
import com.kaiku.cryptospot.data.remote.dto.crypto_list.CryptoListResponse
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun requestCryptoList(
        start: Int,
        limit: Int,
    ) : Result<CryptoListResponse>

    suspend fun isCryptoListCacheExpired(): Boolean

    suspend fun updateAllCryptoList(entities: List<CryptoListingEntity>)

    fun getPagerByQuery(query: String): Pager<Int, CryptoListingEntity>

}